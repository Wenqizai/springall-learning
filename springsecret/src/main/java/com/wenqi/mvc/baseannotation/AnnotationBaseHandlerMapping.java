package com.wenqi.mvc.baseannotation;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liangwenqi
 * @date 2023/4/10
 */
public class AnnotationBaseHandlerMapping implements HandlerMapping {
    private List<HandlerInterceptor> handlerInterceptors;

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        HandlerExecutionChain chain = null;
        Object[] annotationControllers = getAvailableAnnotationControllers();
        for (Object annotationController : annotationControllers) {
            Class<?> clazz = annotationController.getClass();
            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping mapping = clazz.getAnnotation(RequestMapping.class);
                if (matches(mapping, request)) {
                    chain = new HandlerExecutionChain(annotationController);
                    if (!CollectionUtils.isEmpty(getHandlerInterceptors())) {
                        chain.addInterceptors(handlerInterceptors.toArray(new HandlerInterceptor[getHandlerInterceptors().size()]));
                        break;
                    }
                }
            }
        }
        return chain;
    }

    /**
     * 获取所有基于注解的controller
     */
    protected Object[] getAvailableAnnotationControllers() {
        // todo
        return new Object[0];
    }

    /**
     * 与请求url做比对
     * 借助框架工具类:
     * 1. org.springframework.util.PathMatcher
     * 2. org.springframework.web.util.UrlPathHelper
     *
     */
    protected boolean matches(RequestMapping mapping, HttpServletRequest request) {
        // todo
        return true;
    }

    public List<HandlerInterceptor> getHandlerInterceptors() {
        return handlerInterceptors;
    }

    public void setHandlerInterceptors(List<HandlerInterceptor> handlerInterceptors) {
        this.handlerInterceptors = handlerInterceptors;
    }
}
