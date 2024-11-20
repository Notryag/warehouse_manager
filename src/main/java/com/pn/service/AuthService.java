package com.pn.service;

import com.pn.entity.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AuthService {
    public List<Auth> findAuthTree(int userId);

    public List<Auth> allAuthThree();

}
