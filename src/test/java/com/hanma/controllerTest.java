package com.hanma;

import com.hanma.pojo.User;
import com.hanma.service.UserService;
import com.hanma.utils.MailUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 邮件发送
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReggieApplication.class)
public class controllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MailUtils mailUtils;

    @Test
    public void TestAdd(){
        mailUtils.sendMail("yang.cao@hmcamc.com","您好,loin","测试测试");
    }

}
