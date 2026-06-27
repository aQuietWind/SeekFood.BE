package com.seek.food.user.Handler;

import com.seek.food.dto.Common.BizException;
import com.seek.food.dto.Common.ErrorCodeEnum;
import com.seek.food.dto.Common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 捕获自定义业务异常（最高优先级）
    @ExceptionHandler(BizException.class)
    public Result<?> handleBizException(BizException e) {
        ErrorCodeEnum codeEnum = e.getErrorCode();
        return Result.error(codeEnum.getCode(), codeEnum.getDefaultMsg());
    }

    // 参数校验异常
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> handleParamError(IllegalArgumentException e) {
        return Result.error(ErrorCodeEnum.PARAM_ERROR.getCode(), e.getMessage());
    }

    // 系统未知兜底异常（500）
    @ExceptionHandler(Exception.class)
    public Result<?> handleServerError(Exception e) {
        // 打印完整堆栈日志，用于线上排查
        e.printStackTrace();
        return Result.error(ErrorCodeEnum.SERVER_ERROR.getCode(), ErrorCodeEnum.SERVER_ERROR.getDefaultMsg());
    }
}