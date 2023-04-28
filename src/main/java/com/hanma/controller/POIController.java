package com.hanma.controller;

import com.alibaba.fastjson.JSONArray;
import com.hanma.common.Result;
import com.hanma.utils.ExcelUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/poi")
public class POIController {

    @GetMapping("make")
    public Result<Object> poimake() throws Exception {
        XSSFWorkbook web = new XSSFWorkbook();
        Sheet sheet = web.createSheet();
        Sheet sheet2 = web.createSheet("这是啥");
        //3.创建工作表中的行对象
        Row row = sheet.createRow(1); //()为对应行的索引,第二行
        //4.创建工作表中行中的单元格对象
        Cell cell = row.createCell(1);  //()为对应列的索引,第二行第二列的单元格对象
        //5.在单元格写数据
        cell.setCellValue("测试一下单元格");
        File f = new File("test.xlsx");
        //输出时通过流的形式对外输出，包装对应的目标文件
        OutputStream os = new FileOutputStream(f);
        //将内存中的workbook数据写入到流中
        web.write(os);
        os.flush();
        web.close();
        os.close();
        return Result.success("ok");
    }
}
