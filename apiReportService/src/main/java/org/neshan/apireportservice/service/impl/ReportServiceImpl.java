package org.neshan.apireportservice.service.impl;


import org.neshan.apireportservice.dto.InteractionDto;
import org.neshan.apireportservice.dto.ReportDto;
import org.neshan.apireportservice.entity.Interaction;
import org.neshan.apireportservice.entity.Report;
import org.neshan.apireportservice.entity.User;
import org.neshan.apireportservice.entity.model.enums.ReportType;
import org.neshan.apireportservice.entity.model.enums.TrafficLevel;
import org.neshan.apireportservice.repo.InteractionRepository;
import org.neshan.apireportservice.repo.ReportRepository;
import org.neshan.apireportservice.repo.UserRepository;
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
import java.util.Optional;


@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportRepository reportRepository;
    @Autowired
    GeoUtils geoUtils;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    UserRepository userRepository;
    @Autowired
    InteractionRepository interactionRepository;


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

        User user = userRepository.findById(reportDto.getSenderId()).get();

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

    @Override
    public Integer interactWithReport(InteractionDto interactionDto) {

        if (interactionDto == null
                || interactionDto.getInteractionType() == null
                || interactionDto.getReportId() == null
                || interactionDto.getUserId() == null) {

            return HttpStatus.BAD_REQUEST.value();
        }

        Optional<User> userOptional = userRepository.findById(interactionDto.getUserId());

        if (userOptional.isEmpty()) {
            return HttpStatus.NOT_FOUND.value();
        }

        Optional<Report> reportOptional = reportRepository.findById(interactionDto.getReportId());
        if (reportOptional.isEmpty()) {
            return HttpStatus.NOT_FOUND.value();
        }

        Interaction interaction = new Interaction();
        interaction.setUser(userOptional.get());
        interaction.setReport(reportOptional.get());
        interaction.setInteractionType(interactionDto.getInteractionType());

        interactionRepository.save(interaction);

        return HttpStatus.CREATED.value();
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
