package com.pn.controller;

import com.google.code.kaptcha.Producer;
import com.pn.entity.LoginUser;
import com.pn.entity.Result;
import com.pn.entity.User;
import com.pn.service.UserService;
import com.pn.utils.CurrentUser;
import com.pn.utils.DigestUtil;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    //    注入defaultkapcha的bean对象
    @Resource(name = "captchaProducer")
    private Producer producer;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtils tokenUtils;


    @GetMapping("/index")
    public String index() {
        return "hello world";
    }


    @GetMapping("/captcha/captchaImage")
    public void captchaImage(HttpServletResponse response) {


        ServletOutputStream out = null;

        try {
            String text = producer.createText();

            BufferedImage image = producer.createImage(text);

            redisTemplate.opsForValue().set(text, "", 60 * 30, TimeUnit.SECONDS);
            response.setContentType("image/jpeg");
            out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping("/login")
    public Result login(@RequestBody LoginUser loginUser) {
        User user = userService.queryUserByCode(loginUser.getUserCode());
        if (user == null) {
            return Result.err(Result.CODE_ERR_BUSINESS, "account not exits");
        } else {
            if (user.getUserState().equals(WarehouseConstants.USER_STATE_PASS)) {
                String usePwd = user.getUserPwd();
                usePwd = DigestUtil.hmacSign(usePwd);
                if (usePwd.equals(user.getUserPwd())) {
                    CurrentUser currentUser = new CurrentUser(user.getUserId(), user.getUserCode(), user.getUserName());
                    String token = tokenUtils.loginSign(currentUser, user.getUserPwd());
                    return Result.ok(token);
                } else {
                    return Result.err(Result.CODE_ERR_BUSINESS, "password error");
                }
            } else {
                return Result.err(Result.CODE_ERR_BUSINESS, "user not permit");
            }
        }
    }

}
