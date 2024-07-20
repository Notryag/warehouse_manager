package com.pn.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class User {
    private int userId;
    private String userCode;
    private String userName;
    private String userPwd;

    private String userType;

    private String userState;

    private String isDelete;

    private int createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private int updateBy;

    private Date updateTime;

    private String getCode;

    public User() {

    }
    public User(int userId, String userCode, String userName, String userPwd,
                String userType, String userState, String isDelete, int createBy,
                Date createTime, int updateBy, Date updateTime) {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.userPwd = userPwd;
        this.userType = userType;
        this.userState = userState;
        this.isDelete = isDelete;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
    }
}
