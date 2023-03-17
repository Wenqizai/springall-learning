package com.wenqi.mvc.servlet;

import java.util.List;

/**
 * @author liangwenqi
 * @date 2023/3/17
 */
public interface MockServletService {
    List<InfoBean> query(String param1, String param2);
}
