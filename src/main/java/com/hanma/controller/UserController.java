package com.hanma.controller;

import cn.hutool.json.JSONObject;


import com.hanma.annotation.UserLoginToken;
import com.hanma.common.CodeMsg;
import com.hanma.common.Result;
import com.hanma.pojo.User;
import com.hanma.service.UserService;
import com.hanma.service.impl.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：Mr.ZJW
 * @date ：Created 2022/2/26 10:47
 * @description：
 */
@Api(tags = "产品接口")
@RestController
@RequestMapping("/user")
public class UserController {
    //通过LogFactory获取Logger类的实例
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;


    /**
     * 查询用户信息
     * @return
     */
    @UserLoginToken
    /*@PassToken*/
    @GetMapping("/list")
    @ApiOperation("获取账号列表")
    public Result<Object> list(){
        return Result.success(userService.list());
    }


    /**
     * 登录验证
     * @param user
     * @param response
     * @return
     */
    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login" ,method = RequestMethod.GET)
    public Result<Object> login(User user, HttpServletResponse response) {
        logger.error("=====>error");
        logger.warn("=====>warn");
        logger.info("=====>info");
        logger.debug("=====>debug");
        logger.trace("=====>trace");
        JSONObject jsonObject = new JSONObject();
        //获取用户账号密码
        User userForBase = new User();
        userForBase.setId(userService.findByUsername(user).getId());
        userForBase.setUsername(userService.findByUsername(user).getUsername());
        userForBase.setPassword(userService.findByUsername(user).getPassword());
        //判断账号或密码是否正确
        if (!userForBase.getPassword().equals(DigestUtils.md5DigestAsHex((user.getPassword()).getBytes()))) {
            return Result.error(CodeMsg.USER_OR_PASS_ERROR);
        } else {
            String token = tokenService.getToken(userForBase);
            jsonObject.put("token", token);
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            return Result.success(jsonObject);
        }
    }

    @RequestMapping(value = "/logon" ,method = RequestMethod.GET)
    public  Result logon(User user,HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        //1.判断姓名和密码是否为空
        if(user.getUsername().isEmpty()||user.getPassword().isEmpty()){
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        //2.判断用户名是否存在
        User userSys = userService.findByUsername(user);
        System.out.println("userSys:"+userSys);
        if(userSys!=null){
            return Result.error(CodeMsg.USER_IS_USE);
        }
        String password=user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(password);
        userService.save(user);
        jsonObject.put("code",200);
        return  Result.success(jsonObject);
    }
    
}
