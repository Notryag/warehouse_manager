package com.pn.service;

import com.pn.entity.Result;
import com.pn.entity.User;
import com.pn.mapper.UserMapper;
import com.pn.utils.DigestUtil;
import com.pn.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{
    @Autowired
    public UserMapper userMapper;


    public Result saveUser(User user){
        User oldUser = userMapper.findUserByCode(user.getUserCode());
        if (oldUser != null){
            return Result.err(Result.CODE_ERR_BUSINESS, "user already exist");
        }
        String usePwd = DigestUtil.hmacSign(user.getUserPwd());
        user.setUserPwd(usePwd);

        userMapper.insertUser(user);
        return Result.ok("添加用户成功");
    }

    public int deleteUser(int userCode){
        return userMapper.setUserDelete(userCode);
    }



    public User findUserByCode(String userCode) {
        return userMapper.findUserByCode(userCode);
    }

    public Result updateUserName(User user){
        int i = userMapper.updateNameById(user);
        if (i > 1){
            return Result.ok("update success");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "update fail");
    }

    public Page findUserByPage(Page page, User user){
        int userCount = userMapper.selectUserCount(user);

        List<User> userList = userMapper.selectUserPage(page, user);

        page.setTotalNum(userCount);
        page.setResultList(userList);
        return page;
    }
    public Result resetPwd(Integer userId) {

        //创建User对象并保存用户id和加密后的重置密码123456
        User user = new User();
        user.setUserId(userId);
        user.setUserPwd(DigestUtil.hmacSign("123456"));

        //根据用户id修改密码
        int i = userMapper.updatePwdById(user);

        if(i>0){//密码修改成功
            return Result.ok("密码重置成功！");
        }
        //密码修改失败
        return Result.err(Result.CODE_ERR_BUSINESS, "密码重置失败！");
    }
}