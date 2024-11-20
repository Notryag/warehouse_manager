package com.pn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginUser {
    private String userCode;
    private String userPwd;
    private String userState;
    private String verificationCode;
}
