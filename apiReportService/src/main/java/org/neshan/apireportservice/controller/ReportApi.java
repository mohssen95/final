package org.neshan.apireportservice.controller;


import org.locationtech.jts.io.ParseException;
import org.neshan.apireportservice.dto.InteractionDto;
import org.neshan.apireportservice.dto.ReportDto;
import org.neshan.apireportservice.entity.Report;
import org.neshan.apireportservice.service.AccidentService;
import org.neshan.apireportservice.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/reports")
public class ReportApi {


    @Autowired
    ReportService reportService;
    @Autowired
    AccidentService accidentService;


    @PostMapping("")
    public ResponseEntity<Void> addReportByUser(@RequestBody ReportDto reportDto) throws ParseException {
        return ResponseEntity.status(reportService.addReportByUser(reportDto))
                .build();
    }


    @PostMapping("/operator")
    public ResponseEntity<Void> addReportByOperator(@RequestBody ReportDto reportDto) throws ParseException {
        return ResponseEntity.status(reportService.addReportByOperator(reportDto))
                .build();
    }

    @GetMapping("/operator")
    public ResponseEntity<ReportDto> getReportByOperator() {
        ReportDto result = reportService.pickNextReport();

        if (result == null) {
            return ResponseEntity.status(404)
                    .build();
        }

        return ResponseEntity.status(200)
                .body(result);
    }

    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> result = reportService.getAll();
        return ResponseEntity.status(200).body(result);
    }

    @PostMapping("/interaction")
    public ResponseEntity<Integer>addInteractionByUser(@RequestBody InteractionDto interactionDto){
        return ResponseEntity.status(reportService.interactWithReport(interactionDto)).build();
    }


}
