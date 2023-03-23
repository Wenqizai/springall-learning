package com.wenqi.mvc;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liangwenqi
 * @date 2023/3/22
 */
public class FileUploadController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("fileName");
        final String fileComment = multipartRequest.getParameter("comment");
        final byte[] fileContent = multipartFile.getBytes();
        // 保存数据, 返回mav
        return new ModelAndView();
    }
}
