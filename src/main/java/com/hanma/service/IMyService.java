package com.hanma.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface IMyService {
    /**
     * 图片上传
     * @param imageFile
     * @return
     */
    String imageUpload(MultipartFile imageFile);
    /**
     * 图片下载
     * @param imagePath
     * @return
     */
    void imageDownload(String imagePath, HttpServletResponse response);
}
