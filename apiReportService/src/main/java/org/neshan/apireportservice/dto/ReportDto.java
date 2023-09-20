package org.neshan.apireportservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Coordinate;
import org.neshan.apireportservice.entity.model.enums.ReportType;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
public class ReportDto {

     int length;
     Coordinate geom;
     long senderId;
     ReportType reportType;
     private Map<String, Object> extra = new HashMap<>();
}
