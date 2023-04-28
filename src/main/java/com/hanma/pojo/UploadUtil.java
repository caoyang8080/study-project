package com.hanma.pojo;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class UploadUtil {
    @Value("${upload.hostname}")
    private String hostname;
    @Value("${upload.username}")
    private String username;
    @Value("${upload.password}")
    private String password;
    @Value("${upload.targetPath}")
    private String targetPath;
}