package com.pn.mapper;

import com.pn.dto.Unit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UnitMapper {

    public List<Unit> findAllUnit();

}
