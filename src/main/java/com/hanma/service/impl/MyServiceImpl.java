package com.hanma.service.impl;

import com.hanma.service.IMyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MyServiceImpl implements IMyService {
    @Value("${filePath}")
    File imagePath;
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/jpeg", "image/png", "image/jpg","image");
    /**
     * 图片上传
     */
    @Override
    public String imageUpload(MultipartFile image) {
        // 校验图片类型
        String contentType = image.getContentType();
        if (!CONTENT_TYPES.contains(contentType)){
            // 图片类型不合法，直接返回异常
            log.info("文件类型不合法：{}", image.getOriginalFilename());
            return "IMAGE_TYPE_ERROR";
        }
        try {
            //校验是否传空
            if (image == null || image.isEmpty()) {
                return "IMAGE_UPLOAD_EMPTY";
            }
            //获取文件名
            String fileName = image.getOriginalFilename();
            log.info("上传的文件名：" + fileName);
            //设置文件存储路径
            //以时间作为文件夹分类：eg：G:/images/20210615/demo.jpg
            String filePath = "/" + DateFormatUtils.format(new Date(), "yyyyMMdd") + "/" + fileName;
            File file = new File(imagePath, filePath);
            //检测是否存在该目录
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            //写入文件
            image.transferTo(file);
            //todo 将路径写入数据库 :filePath
            return "IMAGE_UPLOAD_SUCCESS";
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return "IMAGE_UPLOAD_ERROR";
    }
    /**
     * 根据图片地址下载
     *
     * @return 图片
     */
    @Override
    public void imageDownload(String path, HttpServletResponse response) {
        FileInputStream inputStream = null;
        try {
            // 判断路径是否为空
            if (StringUtils.isEmpty(path)) {
                throw new RuntimeException("IMAGE_EMPTY");
            }
            //获取文件真实路径
            String fileUrl = imagePath + path;
            System.out.println("path"+path);
            //设置文件头,最后一个参数是设置下载文件名
            response.setHeader("Content-Disposition", "attachment;fileName=" + FilenameUtils.getName(path));
            File file = new File(fileUrl);
            inputStream = new FileInputStream(file);
            OutputStream outputStream = response.getOutputStream();
            IOUtils.copyLarge(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        } finally {
            //关流
            IOUtils.closeQuietly(inputStream);
        }
    }
}
