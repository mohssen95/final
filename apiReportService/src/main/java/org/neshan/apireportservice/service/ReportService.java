package org.neshan.apireportservice.service;

import org.locationtech.jts.io.ParseException;
import org.neshan.apireportservice.dto.InteractionDto;
import org.neshan.apireportservice.dto.ReportDto;
import org.neshan.apireportservice.entity.Report;

import java.sql.Timestamp;
import java.util.List;

public interface ReportService {

    Integer addReportByOperator(ReportDto reportDto) throws ParseException;

    List<Report> getAll();

    Integer addReportByUser(ReportDto reportDto) throws ParseException;

    ReportDto pickNextReport();

    Integer interactWithReport(InteractionDto interactionDto);

    Timestamp getMostAccidentFullHour(String date);

    List<Report> getReportsOnRoad(String road);
}
