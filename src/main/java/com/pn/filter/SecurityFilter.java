package com.pn.filter;

import com.alibaba.fastjson.JSON;
import com.pn.entity.Result;
import com.pn.utils.WarehouseConstants;
import jakarta.servlet.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class SecurityFilter implements Filter {
    private StringRedisTemplate redisTemplate;

    // 成员变量redis模板的set方法
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getServletPath();
        List<String> urlList = Arrays.asList("/captcha/captchaImage", "/login", "/logout");

        // 允许访问的路径
        if (urlList.contains(path) || path.startsWith("/img/upload")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String clientToken = request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);

        if (StringUtils.hasText(clientToken) && redisTemplate.hasKey(clientToken)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // 封装返回结果
            Result result = Result.err(Result.CODE_ERR_UNLOGINED, "please login;");
            String jsonStr = JSON.toJSONString(result);
            response.setContentType("application/json;charset=UTF-8");

            try (PrintWriter out = response.getWriter()) {
                out.println(jsonStr);
                out.flush();
            } catch (IOException e) {
                // 这里可以添加日志记录
                e.printStackTrace();
            }
        }
    }
}
