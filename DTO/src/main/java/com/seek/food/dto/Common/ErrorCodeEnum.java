package com.seek.food.dto.Common;

import org.springframework.http.HttpStatus;

public enum ErrorCodeEnum {
    // 鉴权 40xx
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "身份验证失败"),
    ACCOUNT_FORBIDDEN(403, "账号已被封禁"),
    DATA_NOT_FOUND(404, "未查询到目标数据"),
    METHOD_NOT_ALLOW(405,  "请求方法不允许"),
    BAD_REQUEST_PATH(406,"请求路径无法达到"),
    COOLDOWN_SURVIVE(407,"该操作处于冷却期"),
    OPT_SURVIVE(408,"验证码已存在"),
    OPT_NOT_SURVIVE(409,"验证码不存在"),
    OPT_NOT_SAME(410,"验证码不一致"),
    DATA_SURVIVE(411,"目标数据已存在"),
    TOO_MANY_REQUEST(429, "请求过于频繁，请稍后重试"),
    // 服务异常 50xx
    SERVER_ERROR(500, "服务器内部异常"),
    DOWNSTREAM_UNAVAILABLE(502, "下游服务暂时不可用"),
    SERVICE_TIMEOUT(504, "下游服务请求超时");

    /** 业务错误码（对外返回给前端） */
    private final Integer code;
    /** 默认提示文案 */
    private final String defaultMsg;

    ErrorCodeEnum(Integer code,  String defaultMsg) {
        this.code = code;
        this.defaultMsg = defaultMsg;
    }

    // getter
    public Integer getCode() { return code; }
    public String getDefaultMsg() { return defaultMsg; }
}
