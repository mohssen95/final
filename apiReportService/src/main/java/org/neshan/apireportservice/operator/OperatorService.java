//package org.neshan.apireportservice.operator;
//
//import org.neshan.apireportservice.entity.report.TrafficReport;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class OperatorService {
//    private static final Logger LOGGER = LoggerFactory.getLogger(OperatorService.class);
//
//    @RabbitListener(queues = {"${rabbitmq.queue.json.traffic}"})
//    public void consumeJsonMessage(TrafficReport report){
//        LOGGER.info(String.format("Received JSON message -> %s", report.toString()));
//    }
//
//
//}
