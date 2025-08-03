package com.cgn.talent.common.result;

import lombok.Data;
import java.io.Serializable;

/**
 * 统一响应结果封装类
 *
 * @author CGN
 * @date 2024-01-10
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应代码
     */
    private int code;
    /**
     * 响应代码
     */
    private String sCode;
    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应时间戳
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * 成功响应代码
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 失败响应代码
     */
    public static final int ERROR_CODE = 500;

    public Result() {
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public Result(String sCode, String msg) {
        this.sCode = sCode;
        this.msg = msg;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回成功消息
     */
    public static <T> Result<T> success() {
        return new Result<>(SUCCESS_CODE, "操作成功");
    }

    /**
     * 返回成功消息
     */
    public static <T> Result<T> success(String msg) {
        return new Result<>(SUCCESS_CODE, msg);
    }

    /**
     * 返回成功数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, "操作成功", data);
    }

    /**
     * 返回成功消息和数据
     */
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(SUCCESS_CODE, msg, data);
    }

    /**
     * 返回错误消息
     */
    public static <T> Result<T> error() {
        return new Result<>(ERROR_CODE, "操作失败");
    }

    /**
     * 返回错误消息
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(ERROR_CODE, msg);
    }

    /**
     * 返回错误消息
     */
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg);
    }
    public static <T> Result<T> error(String code, String msg) {
        return new Result<>(code, msg);
    }
}
