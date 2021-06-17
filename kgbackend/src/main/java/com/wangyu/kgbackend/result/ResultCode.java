package com.wangyu.kgbackend.result;

/**
 * @author wongy
 */
public enum ResultCode {
    // 成功
    SUCCESS(200),
    // 失败
    FAIL(400),
    // 没有权限
    UNAUTHORIZED(401),
    // 未找到资源
    NOT_FOUND(404),
    // 服务器内部错误
    INTERNAL_SERVER_ERROR(500);

    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}
