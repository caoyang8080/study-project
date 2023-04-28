package com.hanma.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`user`")
@ApiModel("用户登录信息")
public class User implements Serializable {

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "id", type = IdType.NONE)
    private String id;

    @ApiModelProperty(value = "用户姓名")
    @TableField(value = "username")
    private String username;

    @ApiModelProperty(value = "用户密码")
    @TableField(value = "password")
    private String password;

    private static final long serialVersionUID = 1L;
}
