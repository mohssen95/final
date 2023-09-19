package com.example.operatorservice;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OperatorServiceApplication {
    public static Geometry wktToGeometry(String wellKnownText)
            throws ParseException {

        return new WKTReader().read(wellKnownText);
    }
    public static void main(String[] args) throws ParseException {
        Geometry geometry = wktToGeometry("POINT (5.4 4.2)");
        System.out.println(geometry);


        SpringApplication.run(OperatorServiceApplication.class, args);
    }

}
