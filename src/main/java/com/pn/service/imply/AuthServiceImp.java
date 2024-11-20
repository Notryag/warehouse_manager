package com.pn.service.imply;

import com.alibaba.fastjson2.JSON;
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


}
