package com.pn.service;

import com.pn.dto.Unit;
import com.pn.mapper.UnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {

    @Autowired
    private UnitMapper unitMapper;

    public List<Unit> queryAllUnit() {
        return unitMapper.findAllUnit();
    }
}
