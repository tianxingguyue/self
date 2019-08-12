package com.job.common.handler;


import com.job.core.base.Result;
import com.job.common.exception.core.ExceptionCode;
import com.job.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lizhiliang
 * @createTime 2017-12-13 17:04
 */
@ControllerAdvice(value = {
        "com.cloud.auth",
        "com.cloud.gateway",
        "com.cloud.admin.impl",
        "com.cloud.open.impl",
})  //拦截异常并统一处理
@ResponseBody  //返回结果为json
public class GlobalExceptionHandler {
    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BizException.class)
    public Result baseExceptionHandler(BizException ex) {
        log.error("BizException:", ex);
        return new Result(ex.getCode(), null, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result otherExceptionHandler(Exception ex) {
        log.error("Exception:", ex);
        return new Result(ExceptionCode.SYSTEM_BUSY.getCode(), null, ex.getMessage());
    }
}