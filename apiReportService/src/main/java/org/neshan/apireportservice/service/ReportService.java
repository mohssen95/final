package org.neshan.apireportservice.service;

import org.neshan.apireportservice.dto.InteractionDto;
import org.neshan.apireportservice.dto.ReportDto;
import org.neshan.apireportservice.entity.Report;

import java.util.List;

public interface ReportService {

    Integer addReportByOperator(ReportDto reportDto) ;

    List<Report> getAll();

    Integer addReportByUser(ReportDto reportDto);

    ReportDto pickNextReport();

    Integer interactWithReport(InteractionDto interactionDto);

}
