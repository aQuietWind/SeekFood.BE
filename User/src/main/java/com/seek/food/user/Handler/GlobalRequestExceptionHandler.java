package com.seek.food.user.Handler;

import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import com.seek.food.dto.Common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

//@RestControllerAdvice
public class GlobalRequestExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalRequestExceptionHandler.class);

    // 捕获自定义业务异常（最高优先级）
    @ExceptionHandler(BizException.class)
    public Result<?> handleBizException(BizException e) {
        ErrorCodeEnum codeEnum = e.getErrorCode();
        return Result.error(codeEnum);
    }

    // 参数校验异常
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> handleParamError(IllegalArgumentException e) {
        return Result.error(ErrorCodeEnum.PARAM_ERROR);
    }

    //路径不合法异常
    @ExceptionHandler(NoResourceFoundException.class)
    public Result<?> handlePathException(Exception e) {
        return Result.error(ErrorCodeEnum.BAD_REQUEST_PATH);
    }

    //数据插入冲突异常
    @ExceptionHandler(DuplicateKeyException.class)
    public Result<?> handleUniqueKeyError(DuplicateKeyException e) {
        return Result.error(ErrorCodeEnum.DATA_SURVIVE);
    }

    //方法模式不合法异常
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<?> handleMethodException(Exception e) {
        return Result.error(ErrorCodeEnum.METHOD_NOT_ALLOW);
    }

    // 系统异常
    @ExceptionHandler(RuntimeException.class)
    public Result<?> handleRuntimeError(Exception e) {
        // 打印完整堆栈日志，用于线上排查
        logger.error("服务发生运行时系统异常",e);
        return Result.error(ErrorCodeEnum.SERVER_ERROR);
    }
    // 系统未知兜底异常（500）
    @ExceptionHandler(Exception.class)
    public Result<?> handleServerError(Exception e) {
        // 打印完整堆栈日志，用于线上排查
        logger.error("服务发生未知系统异常",e);
        return Result.error(ErrorCodeEnum.SERVER_ERROR);
    }






}