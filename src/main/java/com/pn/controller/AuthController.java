package com.pn.controller;

import com.pn.entity.Auth;
import com.pn.entity.Result;
import com.pn.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/auth-three")
    public Result AllAuthThree() {
        List<Auth> auths = authService.allAuthThree();
        return Result.ok(auths);
    }
}
