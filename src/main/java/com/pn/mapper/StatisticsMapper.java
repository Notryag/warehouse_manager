package com.pn.mapper;

import com.pn.entity.Statistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatisticsMapper {
    public List<Statistics> statisticsStoreInvent();

}
