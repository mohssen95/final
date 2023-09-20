package org.neshan.apireportservice.service;

import org.neshan.apireportservice.dto.TrafficDto;
import org.neshan.apireportservice.entity.report.TrafficReport;

import java.util.List;

public interface TrafficService {

    TrafficReport addTrafficByOperator(TrafficDto trafficReport) ;

    List<TrafficReport> getAll();

    TrafficReport addTrafficByUser(TrafficDto trafficReport);

    TrafficDto pickNextTrafficReport();
}
