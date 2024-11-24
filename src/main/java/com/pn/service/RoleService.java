package com.pn.service;

import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.mapper.AuthMapper;
import com.pn.mapper.RoleMapper;
import com.pn.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pn.dto.AssignRoleDto;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    public RoleMapper roleMapper;
    @Autowired
    private AuthMapper authMapper;

    public List<Role> getAllRole() {
        return roleMapper.findAllRole();
    }

    public List<Role> queryRolesByUserId(Integer userId) {
        return roleMapper.findRolesByUserId(userId);
    }

    public void assignRole(AssignRoleDto assignRoleDto) {

        //拿到用户id
        Integer userId = assignRoleDto.getUserId();
        //拿到给用户分配的所有角色名
        List<String> roleNameList = assignRoleDto.getRoleCheckList();

        //根据用户id删除给用户已分配的所有角色
        roleMapper.delRoleByUserId(userId);

        //循环添加用户角色关系
        for (String roleName : roleNameList) {
            //根据当前角色名查询当前角色的id
            int roleId = roleMapper.getRoleIdByName(roleName);
            //添加用户角色关系
            roleMapper.insertUserRole(userId, roleId);
        }
    }

    public Page queryRolePage(Page page, Role role) {
        int roleCount = roleMapper.selectRoleCount(role);

        List<Role> roleList = roleMapper.selectRolePage(page, role);

        page.setTotalNum(roleCount);
        page.setResultList(roleList);
        return page;
    }

    public Result saveRole(Role role) {
        Role oldRole = roleMapper.findRoleByNameOrCode(role.getRoleName(), role.getRoleCode());
        if (oldRole != null) {
            return Result.err(Result.CODE_ERR_BUSINESS, "role already exist");
        }
        roleMapper.insertRole(role);
        return Result.ok();
    }

    public Result updateRoleState(Role role) {
        int i = roleMapper.updateRoleState(role);
        if (i > 0) {
            return Result.ok();
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "role already exist");
    }

    public List<Integer> queryAuthIds(Integer roleId) {
        return roleMapper.findAuthIds(roleId);
    }

    public void deleteRole(Integer roleId) {
        int i = roleMapper.deleteRoleById(roleId);
        if (i > 0) {
            authMapper.delAuthByRoleId(roleId);
        }
    }

    public Result updateRoleDesc(Role role) {
        int i = roleMapper.updateDescById(role);
        if (i > 0) {
            return Result.ok();
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "role already exist");
    }
}
