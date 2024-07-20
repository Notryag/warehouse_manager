package com.pn.entity;

import lombok.Data;

@Data
public class LoginUser {
    private String userCode;
    private String userPwd;
    private String userState;
    private String verificationCode;
}
