package com.ht.mall.handler;

import com.ht.mall.exeption.BasicException;
import com.ht.mall.exeption.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BasicException.class)
    public ModelAndView handleBasicException(BasicException e, HttpServletRequest request){
        log.error("request url = {}, errorStatus={}",request.getRequestURL(), e.getErrorCode().getStatus());
        log.error("error",e);
        return new ModelAndView(
                "error/"+e.getErrorCode().getStatus(),
                Map.of(
                        "errorCode",e.getErrorCode().name(),
                        "httpStatus",e.getErrorCode().getStatus()
                ));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView noHandlerFoundHandle(NoHandlerFoundException e, HttpServletRequest request) {
        log.error("error message: {}, url: {}",e.getMessage(),request.getRequestURI());
        log.error("error",e);
        return new ModelAndView(
                "error/4xx",
                Map.of(
                        "errorCode",ErrorCode.NOT_FOUND,
                        "httpStatus",ErrorCode.NOT_FOUND.getStatus()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(
            Exception e, HttpServletRequest request){
        log.error("exception.class, error message: {}, url: {}",e.getMessage(),request.getRequestURI());
        log.info("error",e);
        return new ModelAndView(
                "error/500",
                Map.of(
                        "errorCode", ErrorCode.BAD_REQUEST_4xx,
                        "httpStatus",ErrorCode.BAD_REQUEST_4xx.getStatus()
                ));
    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleServerException(
            RuntimeException e, HttpServletRequest request){
        log.error("runtime exception.class, error message: {}, url: {}",e.getMessage(),request.getRequestURI());
        log.info("error",e);

        return new ModelAndView(
                "error/500",
                Map.of(
                        "errorCode",ErrorCode.INTERNAL_SERVER_ERROR,
                        "httpStatus",ErrorCode.INTERNAL_SERVER_ERROR.getStatus()
                ));
    }

}
