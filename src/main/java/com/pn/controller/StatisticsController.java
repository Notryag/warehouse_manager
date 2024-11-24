package com.pn.controller;

import com.pn.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;


}
