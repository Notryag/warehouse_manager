package com.pn.mapper;

import com.pn.entity.User;
import com.pn.utils.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    public User findUserByCode(String userCode);

    public int selectUserCount(User user);

    public List<User> selectUserPage(@Param("page") Page page, @Param("user") User user);

    public int insertUser(User user);

    public int updateUserState(Integer userId);

    public int updateNameById(User user);

    public int updatePwdById(User user);

    //根据用户id将用户状态修改为删除状态
    public int setUserDelete(Integer userId);
}
