package com.wenqi.mvc;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author liangwenqi
 * @date 2023/3/22
 */
public class FileUploadingCommand {
    private MultipartFile fileToUpload;
    private String comment;

    public MultipartFile getFileToUpload() {
        return fileToUpload;
    }

    public void setFileToUpload(MultipartFile fileToUpload) {
        this.fileToUpload = fileToUpload;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "FileUploadingCommand{" +
                "fileToUpload=" + fileToUpload +
                ", comment='" + comment + '\'' +
                '}';
    }
}
