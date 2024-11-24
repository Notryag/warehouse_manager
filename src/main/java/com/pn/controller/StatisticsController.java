package com.pn.controller;

import com.pn.entity.Result;
import com.pn.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/store-invent")
    public Result statisticsStoreInvent() {
        return Result.ok(statisticsService.statisticsStoreInvent());
    }
}
