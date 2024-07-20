package com.pn.mapper;

import com.pn.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public User findUserByCode(String userCode);
}
