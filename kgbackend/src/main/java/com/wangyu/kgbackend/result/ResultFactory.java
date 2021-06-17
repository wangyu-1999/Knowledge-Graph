package com.wangyu.kgbackend.result;

/**
 * @author wongy
 */
public class ResultFactory {

    public static Result buildSuccessResult(Object data) {
        return buildResult(ResultCode.SUCCESS, "成功", data);
    }

    public static Result buildFailResult(String message) {
        return buildResult(ResultCode.FAIL, message, null);
    }

    private static Result buildResult(ResultCode resultCode, String message, Object data) {
        return buildResult(resultCode.code, message, data);
    }

    private static Result buildResult(int resultCode, String message, Object data) {
        return new Result(resultCode, message, data);
    }
}