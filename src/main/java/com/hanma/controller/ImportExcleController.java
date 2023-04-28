package com.hanma.controller;


import com.alibaba.fastjson.JSONArray;
import com.hanma.annotation.PassToken;
import com.hanma.annotation.UserLoginToken;
import com.hanma.common.Result;
import com.hanma.pojo.UserDemo;
import com.hanma.utils.ExcelUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * excle导入数据
 */
@RestController
@RequestMapping("/import")
public class ImportExcleController {

    @PassToken
    @PostMapping("/import1")
    public Result<Object> importUser1(@RequestPart("file") MultipartFile file) throws Exception {
        JSONArray array = ExcelUtils.readMultipartFile(file);
        System.out.println("导入数据为:" + array);
        return Result.success(array);
    }


    @PostMapping("/import2")
    public Result<Object> importUser2(@RequestPart("file") MultipartFile file) throws Exception {
        List<UserDemo> users = ExcelUtils.readMultipartFile(file, UserDemo.class);
        for (UserDemo user : users) {
            System.out.println(user.toString());
        }
        return Result.success(users);
    }
}
