package com.hanma.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanma.mapper.UserMapper;
import com.hanma.pojo.User;
import com.hanma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int deleteByIds(Long[] ids) {
        return 0;
    }

    @Override
    public int addUser(User user) {
        return 0;
    }

    /**
     * 判断用户名
     * @param user
     * @return
     */
    public User findByUsername(User user){
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername,user.getUsername()));
    }

    public User findUserById(String userId) {
        return userMapper.selectById(userId);
    }

}
