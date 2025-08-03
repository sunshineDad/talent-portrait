package com.cgn.talent.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回码枚举
 *
 * @author CGN
 * @date 2024-01-10
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // 成功
    SUCCESS("00000", "操作成功"),

    // 系统错误：500xx
    ERROR("50000", "系统内部错误"),
    SYSTEM_BUSY("50001", "系统繁忙，请稍后重试"),

    // 参数错误：400xx
    PARAM_ERROR("40000", "参数错误"),
    PARAM_IS_NULL("40001", "参数为空"),
    PARAM_TYPE_ERROR("40002", "参数类型错误"),
    PARAM_VALID_ERROR("40003", "参数校验失败"),

    // 用户错误：401xx
    USER_NOT_LOGIN("40100", "用户未登录"),
    USER_NOT_EXISTS("40101", "用户不存在"),
    USER_PASSWORD_ERROR("40102", "用户名或密码错误"),
    USER_ACCOUNT_LOCKED("40103", "用户账户被锁定"),
    USER_TOKEN_EXPIRED("40104", "token已过期"),

    // 权限错误：403xx
    NO_PERMISSION("40300", "没有权限"),

    // 数据错误：404xx
    DATA_NOT_EXISTS("40400", "数据不存在"),
    DATA_ALREADY_EXISTS("40401", "数据已存在"),
    DATA_ERROR("40402", "数据异常"),

    // 业务错误：500xx
    BUSINESS_ERROR("50000", "业务异常"),

    // 文件错误：506xx
    FILE_NOT_EXISTS("50600", "文件不存在"),
    FILE_UPLOAD_ERROR("50601", "文件上传失败"),
    FILE_DOWNLOAD_ERROR("50602", "文件下载失败"),
    FILE_DELETE_ERROR("50603", "文件删除失败");

    /**
     * 返回码
     */
    private final String code;

    /**
     * 返回信息
     */
    private final String message;
}
