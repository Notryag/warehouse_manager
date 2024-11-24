package com.pn.service;

import com.pn.entity.Statistics;
import com.pn.mapper.StatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    private StatisticsMapper  statisticsMapper;

    public List<Statistics> statisticsStoreInvent() {
        return statisticsMapper.statisticsStoreInvent();
    }

}
