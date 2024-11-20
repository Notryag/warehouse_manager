package com.pn.controller;


import com.pn.entity.Result;
import com.pn.entity.User;
import com.pn.service.AuthService;
import com.pn.service.UserService;
import com.pn.utils.CurrentUser;
import com.pn.utils.Page;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthService authService;


    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserService userService;

//    @GetMapping("/user-list")
//    public Result UserListPage(Page page) {
//
//    }


    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int updateBy = currentUser.getUserId();

        user.setUpdateBy(updateBy);
        user.setUpdateTime(new Date());
        Result result = userService.saveUser(user);
        return result;
    }

    @PostMapping("/deleteUser/{userId}")
    public Result deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return Result.ok("delete success");
    }

    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody User user, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int updateBy = currentUser.getUserId();
        user.setUpdateBy(updateBy);
        user.setUpdateTime(new Date());
        Result result = userService.updateUserName(user);
        return result;
    }

    @PostMapping("/user-list")
    public Result userList(Page page, User user) {
        page = userService.findUserByPage(page, user);
        return Result.ok(page);
    }
}
