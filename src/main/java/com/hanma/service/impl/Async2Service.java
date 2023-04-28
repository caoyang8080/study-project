package com.hanma.service.impl;

import com.hanma.pojo.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class Async2Service {
    @Async
    public void async2() throws  InterruptedException{
        System.out.println("异步线程2开始");
        ArrayList<User> objects = new ArrayList<>();
        User user = new User();
        user.setPassword("2323");
        user.setUsername("ddd");
        objects.add(user);
        Thread.sleep(5000);
        System.out.println("异步线程2结束");
    }
}
