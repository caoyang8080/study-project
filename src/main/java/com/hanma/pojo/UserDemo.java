package com.hanma.pojo;

import com.hanma.utils.ExcelImport;
import lombok.Data;

/**
 * excle导入数据封装
 */
@Data
public class UserDemo {
    private  int rowNum;

    @ExcelImport(value = "姓名",required = true, unique = true)
    private String  name;
    @ExcelImport("年龄")
    private Integer age;
    @ExcelImport( value = "性别",kv = "1-男;2-女")
    private Integer sex;
    @ExcelImport(value = "电话",maxLength = 11,unique = true)
    private String tel;
    @ExcelImport("城市")
    private String city;
    @ExcelImport("头像")
    private String avatar;
    /**错误提示*/
    private String rowTips;
    /**原始数据*/
    private String rowData;

}
