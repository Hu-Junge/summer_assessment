package com.hu.choutuan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hu.choutuan.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
