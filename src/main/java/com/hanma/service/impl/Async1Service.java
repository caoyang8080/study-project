package com.hanma.service.impl;

import com.hanma.pojo.User;
import com.sun.corba.se.impl.orbutil.closure.Future;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class Async1Service {

    @Async
    public void async1() throws  InterruptedException{
        System.out.println("异步线程1开始");
        ArrayList<User> objects = new ArrayList<>();
        User user = new User();
        user.setPassword("123");
        user.setUsername("eee");
        objects.add(user);
        Thread.sleep(3000);
        System.out.println("异步线程1结束");
    }
}
