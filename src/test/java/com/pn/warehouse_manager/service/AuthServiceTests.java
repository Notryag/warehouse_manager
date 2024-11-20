package com.pn.warehouse_manager.service;


import com.pn.entity.Auth;
import com.pn.service.imply.AuthServiceImp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AuthServiceTests {

    @Autowired
    private AuthServiceImp authServiceImp;


    @Test
    public void test() {
        List<Auth> authTree = authServiceImp.findAuthTree(1);
        System.out.println(authTree);
    }
}
