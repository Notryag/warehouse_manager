package com.pn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Result {
    public static final int CODE_OK = 200;
    public static final int CODE_ERR_BUSINESS = 501;
    public static final int CODE_ERR_UNLOGINED = 502;
    //系统错误
    public static final int CODE_ERR_SYS = 503;

    private int code;
    private boolean success;
    private String message;

    private Object data;

    public static Result ok() {
        return new Result(CODE_OK, true, null, null);
    }

    public static Result ok(String message) {
        return new Result(CODE_OK, true, message, null);
    }

    public static Result ok(Object data) {
        return new Result(CODE_OK, true, null, data);
    }

    public static Result ok(String message, Object data) {
        return new Result(CODE_OK, true, message, data);
    }

    public static Result err(int code, String message) {
        return new Result(code, false, message, null);
    }

    public static Result err(int code, String message, Object data) {
        return new Result(code, false, message, data);
    }
}
