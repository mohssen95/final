package org.neshan.apireportservice.service.impl;


import org.neshan.apireportservice.dto.TrafficDto;
import org.neshan.apireportservice.entity.User;
import org.neshan.apireportservice.entity.model.enums.TrafficType;
import org.neshan.apireportservice.entity.report.TrafficReport;
import org.neshan.apireportservice.repo.TrafficRepository;
import org.neshan.apireportservice.repo.UserRepo;
import org.neshan.apireportservice.service.TrafficService;
import org.neshan.apireportservice.utiles.GeoUtils;
import org.postgis.Point;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class TrafficServiceImpl implements TrafficService {

    @Autowired
    TrafficRepository trafficRepository;
    @Autowired
    GeoUtils geoUtils;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    UserRepo userRepo;


    @Override
    public TrafficReport addTrafficByOperator(TrafficDto trafficDto) {

        RAtomicLong fromCache = getKeyIdFromCache(trafficDto);

        if (!fromCache.isExists()) {
            //todo ??

            return null;
        }

        if (fromCache.get() == 1) {
            return null;
        }


        return cacheAndSaveTrustedTraffic(trafficDto, fromCache);
    }


    private RAtomicLong getKeyIdFromCache(TrafficDto trafficDto) {
        String keyId = geoUtils.generateHashKey(trafficDto, 22);
        RAtomicLong atomicLong = redissonClient.getAtomicLong(keyId);
        return atomicLong;
    }

    @Override
    public List<TrafficReport> getAll() {
        return trafficRepository.findAll();
    }

    @Override
    public TrafficReport addTrafficByUser(TrafficDto trafficDto) {
        RAtomicLong fromCache = getKeyIdFromCache(trafficDto);

        if (fromCache.isExists()) {
            // todo handle
            return null;
        }

        User user = userRepo.findById(trafficDto.getSenderId()).get();

        if (user.getTrust() < .5 || trafficDto.getTrafficType() == TrafficType.HIGH) {
            fromCache.set(0);
            fromCache.expire(Duration.of(5, ChronoUnit.MINUTES));
            RQueue<TrafficDto> trafficsToBeHandled = redissonClient.getQueue("trafficsQueue");

            trafficsToBeHandled.add(trafficDto);
            return null;
        }

        return cacheAndSaveTrustedTraffic(trafficDto, fromCache);

    }

    @Override
    public TrafficDto pickNextTrafficReport() {
        RQueue<TrafficDto> trafficsToBeHandled = redissonClient.getQueue("trafficsQueue");
        return trafficsToBeHandled.poll();
    }

    private TrafficReport cacheAndSaveTrustedTraffic(TrafficDto trafficDto, RAtomicLong fromCache) {
        //todo mapstruct
        TrafficReport newTraffic = new TrafficReport();
        newTraffic.setGeom(new Point(trafficDto.getGeom().x, trafficDto.getGeom().y));
        newTraffic.setTrafficType(trafficDto.getTrafficType());

        TrafficReport trafficReport = trafficRepository.save(newTraffic);

        fromCache.set(1);
        fromCache.expire(Duration.of(2, ChronoUnit.MINUTES));
        return trafficReport;
    }
}
