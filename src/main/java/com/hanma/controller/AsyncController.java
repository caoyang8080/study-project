package com.hanma.controller;

import com.hanma.service.impl.Async1Service;
import com.hanma.service.impl.Async2Service;
import org.apache.poi.ss.formula.functions.Now;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    private Async1Service async1Service;

    @Autowired
    private Async2Service async2Service;

    @GetMapping("/test")
    public void testAsync() throws Exception{
        async1Service.async1();
        async2Service.async2();;

    }
}
