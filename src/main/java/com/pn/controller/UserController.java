package com.pn.controller;


import com.pn.dto.AssignRoleDto;
import com.pn.entity.Auth;
import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.entity.User;
import com.pn.service.AuthService;
import com.pn.service.RoleService;
import com.pn.service.UserService;
import com.pn.utils.CurrentUser;
import com.pn.utils.Page;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthService authService;


    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/auth-list")
    public Result authList(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String clientToken) {
        //从前端归还的token中解析出当前登录用户的信息
        CurrentUser currentUser = tokenUtils.getCurrentUser(clientToken);
        //根据用户id查询用户权限(菜单)树
        List<Auth> authTreeList = authService.findAuthTree(currentUser.getUserId());
        //响应
        return Result.ok(authTreeList);
    }

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

    @GetMapping("/user-list")
    public Result userList(Page page, User user) {
        page = userService.findUserByPage(page, user);
        return Result.ok(page);
    }

    @RequestMapping("/assignRole")
    public Result assignRole(@RequestBody AssignRoleDto assignRoleDto){
        //执行业务
        roleService.assignRole(assignRoleDto);
        //响应
        return Result.ok("分配角色成功！");
    }

    @GetMapping("/updatePwd/{userId}")
    public Result resetPassWord(@PathVariable int userId) {
        Result result = userService.resetPwd(userId);
        //响应
        return result;
    }
    @RequestMapping("/user-role-list/{userId}")
    public Result userRoleList(@PathVariable Integer userId){
        //执行业务
        List<Role> roleList = roleService.queryRolesByUserId(userId);
        //响应
        return Result.ok(roleList);
    }
}
