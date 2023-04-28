package com.hanma.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanma.pojo.User;
import org.springframework.stereotype.Service;


public interface UserService extends IService<User> {


    int deleteByIds(Long[] ids);

    int addUser(User user);

    User findByUsername(User user);

    User findUserById(String userId);
}
