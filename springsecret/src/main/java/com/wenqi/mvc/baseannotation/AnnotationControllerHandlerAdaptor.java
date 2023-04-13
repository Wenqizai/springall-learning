package com.wenqi.mvc.baseannotation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author liangwenqi
 * @date 2023/4/13
 */
public class AnnotationControllerHandlerAdaptor implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        Class<?> clazz = handler.getClass();
        return clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(RequestMapping.class);
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Method[] methods = handler.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                ModelAndView mav = invokeAndReturn(method, handler, request);
                return mav;
            }
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return null;
    }

    private ModelAndView invokeAndReturn(Method method, Object handler, HttpServletRequest request) throws Exception{
        // 1. 使用DataBinder或者其他装备将request参数绑定到方法参数
        Object[] parameterValues = bind(request, method);
        // 2. 使用绑定否获取的相应参数调用方法
        Object returnValue = method.invoke(handler, parameterValues);
        ModelAndView mav = new ModelAndView();
        // 3. 将方法返回的对象转发成ModelAndView返回
        if (returnValue instanceof String) {
            mav.setViewName((String) returnValue);
        } else if (returnValue instanceof ModelMap) {
            mav.addAllObjects((Map) returnValue);
        }
        return mav;
    }

    private Object[] bind(HttpServletRequest request, Method method) {
        return new Object[0];
    }

    @Override
    public long getLastModified(HttpServletRequest request, Object handler) {
        return 0;
    }
}
