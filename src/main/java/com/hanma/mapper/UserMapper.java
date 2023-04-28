package com.hanma.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanma.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
