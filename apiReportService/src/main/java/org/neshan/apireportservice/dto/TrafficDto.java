package org.neshan.apireportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Coordinate;
import org.neshan.apireportservice.entity.model.enums.ReportType;
import org.neshan.apireportservice.entity.model.enums.TrafficType;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TrafficDto {
    private int length;
    private Coordinate geom;
    private long senderId;
    private ReportType reportType;
    private TrafficType trafficType;

}
