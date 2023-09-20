package org.neshan.apireportservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Coordinate;
import org.neshan.apireportservice.entity.model.enums.ReportType;
import org.neshan.apireportservice.entity.model.enums.TrafficType;



@Getter
@Setter
public class TrafficDto {

     int length;
     Coordinate geom;
     long senderId;
     TrafficType trafficType;
}
