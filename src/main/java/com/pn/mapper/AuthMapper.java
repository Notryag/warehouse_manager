package com.pn.mapper;


import com.pn.entity.Auth;

import java.util.List;

public interface AuthMapper {
    public List<Auth> findAllAuth(int userId);

    public List<Auth> getAllAuth();
}
