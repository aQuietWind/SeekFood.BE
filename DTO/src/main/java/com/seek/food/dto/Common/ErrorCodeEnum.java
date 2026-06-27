package com.seek.food.dto.Common;

import org.springframework.http.HttpStatus;

public enum ErrorCodeEnum {
    // 鉴权 40xx
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, "身份验证失败"),
    ACCOUNT_FORBIDDEN(402, HttpStatus.FORBIDDEN, "账号已被封禁"),
    PARAM_ERROR(403, HttpStatus.BAD_REQUEST, "参数错误"),
    DATA_NOT_FOUND(404, HttpStatus.NOT_FOUND, "未查询到目标数据"),
    METHOD_NOT_ALLOW(405, HttpStatus.METHOD_NOT_ALLOWED, "请求方法不允许"),
    TOO_MANY_REQUEST(429, HttpStatus.TOO_MANY_REQUESTS, "请求过于频繁，请稍后重试"),
    // 服务异常 50xx
    SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "服务器内部异常"),
    DOWNSTREAM_UNAVAILABLE(502, HttpStatus.BAD_GATEWAY, "下游服务暂时不可用"),
    SERVICE_TIMEOUT(504, HttpStatus.GATEWAY_TIMEOUT, "下游服务请求超时");

    /** 业务错误码（对外返回给前端） */
    private final Integer code;
    /** HTTP标准状态码 */
    private final HttpStatus httpStatus;
    /** 默认提示文案 */
    private final String defaultMsg;

    ErrorCodeEnum(Integer code, HttpStatus httpStatus, String defaultMsg) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.defaultMsg = defaultMsg;
    }

    // getter
    public Integer getCode() { return code; }
    public HttpStatus getHttpStatus() { return httpStatus; }
    public String getDefaultMsg() { return defaultMsg; }
}
