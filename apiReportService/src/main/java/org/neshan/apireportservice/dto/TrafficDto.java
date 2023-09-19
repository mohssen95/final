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
     ReportType reportType;
     TrafficType trafficType;

     public int getLength() {
          return length;
     }

     public void setLength(int length) {
          this.length = length;
     }

     public Coordinate getGeom() {
          return geom;
     }

     public void setGeom(Coordinate geom) {
          this.geom = geom;
     }

     public long getSenderId() {
          return senderId;
     }

     public void setSenderId(long senderId) {
          this.senderId = senderId;
     }

     public ReportType getReportType() {
          return reportType;
     }

     public void setReportType(ReportType reportType) {
          this.reportType = reportType;
     }

     public TrafficType getTrafficType() {
          return trafficType;
     }

     public void setTrafficType(TrafficType trafficType) {
          this.trafficType = trafficType;
     }
}
