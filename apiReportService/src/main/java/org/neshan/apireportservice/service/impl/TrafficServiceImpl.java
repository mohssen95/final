package org.neshan.apireportservice.service.impl;


import org.neshan.apireportservice.dto.TrafficDto;
import org.neshan.apireportservice.entity.report.TrafficReport;
import org.neshan.apireportservice.repo.TrafficRepository;
import org.neshan.apireportservice.service.TrafficService;
import org.postgis.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TrafficServiceImpl implements TrafficService {

    @Autowired
    TrafficRepository trafficRepository;


    @Override
    public TrafficReport addNew(TrafficDto trafficDto)  {

        TrafficReport newTraffic=new TrafficReport();
        Point p=new Point(trafficDto.getGeom().x,trafficDto.getGeom().y);
        newTraffic.setGeom(p);
        newTraffic.setTrafficType(trafficDto.getTrafficType());
       return trafficRepository.save(newTraffic);
    }

    @Override
    public List<TrafficReport> getAll() {
        return trafficRepository.findAll();
    }
}
