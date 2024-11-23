package com.pn.service.imply;

import com.alibaba.fastjson2.JSON;
import com.pn.dto.AssignAuthDto;
import com.pn.entity.Auth;
import com.pn.mapper.AuthMapper;
import com.pn.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImp implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<Auth> findAuthTree(int userId) {
        String authTreeListJson = redisTemplate.opsForValue().get(userId + ":authTree");
        if (StringUtils.hasText(authTreeListJson)) {
            List<Auth> authTreeList = JSON.parseArray(authTreeListJson, Auth.class);
            return authTreeList;
        }
        List<Auth> allAuthList = authMapper.findAllAuth(userId);
        List<Auth> authTreeList = allAuthToAuthTree(allAuthList, 0);
        redisTemplate.opsForValue().set(userId + ":authTree", JSON.toJSONString(authTreeList));


        return authTreeList;
    }

    @Override
    public List<Auth> allAuthThree() {
        String allAuthThreeJson = redisTemplate.opsForValue().get("all:authThree");
        if (StringUtils.hasText(allAuthThreeJson)) {
            List<Auth> allAuthThreeList = JSON.parseArray(allAuthThreeJson, Auth.class);
            return allAuthThreeList;
        }

        return List.of();
    }

    private List<Auth> allAuthToAuthTree(List<Auth> allAuthList, int parentId) {
        ArrayList<Auth> auths = new ArrayList<>();
        for (Auth auth: allAuthList) {
            if (auth.getParentId() == parentId) {
                auths.add(auth);
            }
        }
        for (Auth auth: auths) {
            List<Auth> chidAuthList = allAuthToAuthTree(allAuthList, auth.getAuthId());
            auth.setChildAuth(chidAuthList);
        }
        return auths;
    }

    public List<Auth> allAuthTree() {
        //先从redis中查询缓存,查到的是整个权限(菜单)树List<Auth>转的json串
        String allAuthTreeJson = redisTemplate.opsForValue().get("all:authTree");
        if(StringUtils.hasText(allAuthTreeJson)){//redis中查到缓存
            //将json串转回整个权限(菜单)树List<Auth>并返回
            List<Auth> allAuthTreeList = JSON.parseArray(allAuthTreeJson, Auth.class);
            return allAuthTreeList;
        }
        //redis中没有查到缓存,从数据库表中查询所有权限(菜单)
        List<Auth> allAuthList = authMapper.getAllAuth();
        //将所有权限(菜单)List<Auth>转成整个权限(菜单)树List<Auth>
        List<Auth> allAuthTreeList = allAuthToAuthTree(allAuthList, 0);
        //将整个权限(菜单)树List<Auth>转成json串并保存到redis
        redisTemplate.opsForValue().set("all:authTree", JSON.toJSONString(allAuthTreeList));
        //返回整个权限(菜单)树List<Auth>
        return allAuthTreeList;
    }

    public void assignAuth(AssignAuthDto assignAuthDto) {
        Integer roleId = assignAuthDto.getRoleId();
        List<Integer> authIds = assignAuthDto.getAuthIds();
        authMapper.delAuthByRoleId(roleId);

        for (Integer authId: authIds) {
            authMapper.insertRoleAuth(roleId, authId);
        }


    }

}
