package com.ht.mall.argumentresolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("support 파라미터 실행");

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasMemberIdType = Long.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasMemberIdType;
    }

    @Override
    //컨트롤러 호출 직전에 호출되어 필요한 파라미터 정보를 생성함. (세션에 있는 memberId 객체를 찾아서 있으면 반환, 없으면 null 반환)
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolve argument 실행");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        HttpSession session = request.getSession(false);
        if(session==null){
            log.info("session is null");
             return null;
        }

        return session.getAttribute("memberId");
    }
}
