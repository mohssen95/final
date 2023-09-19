package org.neshan.apireportservice.service.impl;


import org.neshan.apireportservice.dto.TrafficDto;
import org.neshan.apireportservice.entity.report.TrafficReport;
import org.neshan.apireportservice.repo.TrafficRepository;
import org.neshan.apireportservice.service.TrafficService;
import org.neshan.apireportservice.utiles.GeoUtils;
import org.postgis.Point;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class TrafficServiceImpl implements TrafficService {

    @Autowired
    TrafficRepository trafficRepository;
    @Autowired
    GeoUtils geoUtils;
    @Autowired
    RedissonClient redissonClient;


    @Override
    public TrafficReport addNew(TrafficDto trafficDto)  {


        //make key for hmap redisson
        //key > type/tileId/time block
        String tgtkey=geoUtils.generateHashKey(trafficDto,22);
//        check in redis cache hmap
        RMapCache<String ,Boolean > map = redissonClient.getMapCache("reportsMap");
        System.out.println(tgtkey);
        if(map.containsKey(tgtkey)){
            if(map.get(tgtkey)){
                System.out.println("tekrari");return null;}
            else {System.out.println("tekrari ing db ");return null;}
        }

        map.put(tgtkey,false);
        TrafficReport newTraffic=new TrafficReport();
        Point p=new Point(trafficDto.getGeom().x,trafficDto.getGeom().y);
        newTraffic.setGeom(p);
        newTraffic.setTrafficType(trafficDto.getTrafficType());

        map.put(tgtkey,true,10000,TimeUnit.MILLISECONDS);
        long d=map.remainTimeToLive(tgtkey);
        System.out.println(d);
        map.put(tgtkey,true,d+4000,TimeUnit.MILLISECONDS);
        System.out.println(map.remainTimeToLive(tgtkey));

        //set flag hmap for key true
       return trafficRepository.save(newTraffic);


    }

    @Override
    public List<TrafficReport> getAll() {
        return trafficRepository.findAll();
    }
}
