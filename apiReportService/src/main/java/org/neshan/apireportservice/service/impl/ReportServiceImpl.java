package org.neshan.apireportservice.service.impl;


import org.neshan.apireportservice.dto.ReportDto;
import org.neshan.apireportservice.entity.User;
import org.neshan.apireportservice.entity.model.enums.ReportType;
import org.neshan.apireportservice.entity.model.enums.TrafficLevel;
import org.neshan.apireportservice.entity.report.Report;
import org.neshan.apireportservice.repo.ReportRepository;
import org.neshan.apireportservice.repo.UserRepo;
import org.neshan.apireportservice.service.ReportService;
import org.neshan.apireportservice.utiles.GeoUtils;
import org.postgis.Point;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportRepository reportRepository;
    @Autowired
    GeoUtils geoUtils;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    UserRepo userRepo;


    @Override
    public Integer addReportByOperator(ReportDto reportDto) {

        RAtomicLong cachedTraffic = getKeyIdFromCache(reportDto);

        if (!cachedTraffic.isExists()) {
            //todo ??

            return HttpStatus.NOT_FOUND.value();
        }

        if (cachedTraffic.get() == 1) {
            return HttpStatus.CONFLICT.value();
        }


        cacheAndSaveTrustedTraffic(reportDto, cachedTraffic);
        return HttpStatus.CREATED.value();
    }


    private RAtomicLong getKeyIdFromCache(ReportDto reportDto) {
        String keyId = geoUtils.generateHashKey(reportDto, 22);
        RAtomicLong atomicLong = redissonClient.getAtomicLong(keyId);
        return atomicLong;
    }

    @Override
    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    @Override
    public Integer addReportByUser(ReportDto reportDto) {

        if (reportDto == null || reportDto.getReportType() == null) {
            return HttpStatus.BAD_REQUEST.value();
        }

        RAtomicLong fromCache = getKeyIdFromCache(reportDto);

        if (fromCache.isExists()) {
            // todo handle
            return HttpStatus.CONFLICT.value();
        }

        User user = userRepo.findById(reportDto.getSenderId()).get();

        if (operatorConfirmationNeeded(reportDto, user)) {
            fromCache.set(0);
            fromCache.expire(Duration.of(5, ChronoUnit.MINUTES));
            RQueue<ReportDto> reportsToBeHandled = redissonClient.getQueue("reportsQueue");

            reportsToBeHandled.add(reportDto);
            return HttpStatus.ACCEPTED.value();
        }

        cacheAndSaveTrustedTraffic(reportDto, fromCache);

        return HttpStatus.CREATED.value();

    }

    private static boolean operatorConfirmationNeeded(ReportDto reportDto, User user) {
        return user.getTrust() < .5
                || (reportDto.getReportType() == ReportType.TRAFFIC &&
                TrafficLevel.HIGH.toString().equals(reportDto.getExtra().get("level")));
    }

    @Override
    public ReportDto pickNextReport() {
        RQueue<ReportDto> reportsToBeHandled = redissonClient.getQueue("reportsQueue");
        return reportsToBeHandled.poll();
    }

    private Report cacheAndSaveTrustedTraffic(ReportDto reportDto, RAtomicLong fromCache) {
        //todo mapstruct
        Report newTraffic = new Report();
        newTraffic.setGeom(new Point(reportDto.getGeom().x, reportDto.getGeom().y));
        newTraffic.setReportType(ReportType.TRAFFIC);
        newTraffic.setSenderId(reportDto.getSenderId());
        newTraffic.setExtra(reportDto.getExtra());

        Report report = reportRepository.save(newTraffic);

        fromCache.set(1);
        fromCache.expire(Duration.of(2, ChronoUnit.MINUTES));
        return report;
    }
}
