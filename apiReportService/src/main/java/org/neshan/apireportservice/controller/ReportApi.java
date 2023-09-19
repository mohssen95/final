package org.neshan.apireportservice.controller;


import lombok.Getter;
import org.locationtech.jts.io.ParseException;
import org.neshan.apireportservice.dto.ReportResponse;
import org.neshan.apireportservice.dto.TrafficDto;
import org.neshan.apireportservice.entity.report.Report;
import org.neshan.apireportservice.entity.report.TrafficReport;
import org.neshan.apireportservice.service.AccidentService;
import org.neshan.apireportservice.service.impl.AccidentServiceImpl;
import org.neshan.apireportservice.service.impl.TrafficServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/reports" )
public class ReportApi {


    @Autowired
    TrafficServiceImpl trafficService;
    @Autowired
    AccidentServiceImpl accidentService;


    @PostMapping("/traffics")
    public TrafficReport addReport(@RequestBody TrafficDto trafficReport) throws ParseException {
        return trafficService.addNew(trafficReport);
    }

    @GetMapping("/traffics")
    public List<TrafficReport> getAllReports(){
        return trafficService.getAll();
    }




}
