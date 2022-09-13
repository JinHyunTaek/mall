package com.ht.mall.handler;

import com.ht.mall.exeption.BasicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BasicException.class)
    public ModelAndView handleBasicException(BasicException e, HttpServletRequest request){
        log.error("request url = {}, errorStatus={}",request.getRequestURL(), e.getErrorCode().getStatus());
        return new ModelAndView(
                "error/"+e.getErrorCode().getStatus(),
                Map.of(
                        "errorCode",e.getErrorCode().name(),
                        "httpStatus",e.getErrorCode().getStatus()
                ));
    }

}
