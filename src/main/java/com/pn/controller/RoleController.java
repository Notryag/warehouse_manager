package com.pn.controller;

import com.pn.dto.AssignAuthDto;
import com.pn.entity.Result;
import com.pn.entity.Role; // Assuming Role is a custom entity
import com.pn.service.RoleService;
import com.pn.service.imply.AuthServiceImp;
import com.pn.utils.CurrentUser;
import com.pn.utils.Page;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private AuthServiceImp authServiceImp;

    @GetMapping("/role-list")
    public Result queryAllRole() {
        List<Role> allRole = roleService.getAllRole();
        if (allRole == null || allRole.isEmpty()) {
            return Result.err(Result.CODE_ERR_BUSINESS, "no role found");
        }
        return Result.ok(allRole);
    }

    @GetMapping("/role-page-list")
    public Result roleListPage(Page page, Role role) {
        page = roleService.queryRolePage(page, role);
        return Result.ok(page);
    }

    @PostMapping("/role-add")
    public Result addRole(@RequestBody Role role,
                          @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int createBy = currentUser.getUserId();
        role.setCreateBy(createBy);

        Result result = roleService.saveRole(role);
        return result;
    }

    @RequestMapping("/role-state-update")
    public Result updateRoleState(@RequestBody Role role,
                                  @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int updateBy = currentUser.getUserId();
        role.setUpdateBy(updateBy);

        role.setUpdateBy(updateBy);
        role.setUpdateTime(new Date());
        Result result = roleService.updateRoleState(role);
        return result;
    }

    @GetMapping("/auth-grant")
    public Result authGrant(@RequestBody AssignAuthDto assignAuthDto) {
        authServiceImp.assignAuth(assignAuthDto);
        return Result.ok();
    }

    @GetMapping("/role-delete/{roleId}")
    public Result deleteRole(@PathVariable int roleId) {
        roleService.deleteRole(roleId);
        return Result.ok();
    }

    @PostMapping("/role-update")
    public Result updateRole(@RequestBody Role role,
                             @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int updateBy = currentUser.getUserId();
        role.setUpdateBy(updateBy);

        Result result = roleService.updateRoleDesc(role);
        return result;
    }
}