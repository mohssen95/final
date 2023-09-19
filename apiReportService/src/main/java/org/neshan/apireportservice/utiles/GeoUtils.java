package org.neshan.apireportservice.utiles;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Component;


@Component
public class GeoUtils {

    public Point coordinateToPoint(Coordinate coordinate) throws ParseException {
        String wkt="POINT ("+coordinate.x+" "+coordinate.y+")";
        System.out.println(wkt);
        System.out.println((Point) wktToGeometry(wkt));
        return (Point) wktToGeometry("POINT (1 1)");
    }

    public Geometry wktToGeometry(String wellKnownText) throws ParseException {
        return new WKTReader().read(wellKnownText);
    }

    public String latLonToTileZXY(double lat, double lon, int z) {
        final int MIN_ZOOM_LEVEL = 0;
        final int MAX_ZOOM_LEVEL = 22;
        final double MIN_LAT = -85.051128779807;
        final double MAX_LAT = 85.051128779806;
        final double MIN_LON = -180.0;
        final double MAX_LON = 180.0;

        if ((z < MIN_ZOOM_LEVEL) || (z > MAX_ZOOM_LEVEL))
        {
            throw new IllegalArgumentException("Zoom level value is out of range [" +
                    Integer.toString(MIN_ZOOM_LEVEL) + ", " +
                    Integer.toString(MAX_ZOOM_LEVEL) + "]");
        }

        if (!Double.isFinite(lat) || (lat < MIN_LAT) || (lat > MAX_LAT))
        {
            throw new IllegalArgumentException("Latitude value is out of range [" +
                    Double.toString(MIN_LAT) + ", " +
                    Double.toString(MAX_LAT) + "]");
        }

        if (!Double.isFinite(lon) || (lon < MIN_LON) || (lon > MAX_LON))
        {
            throw new IllegalArgumentException("Longitude value is out of range [" +
                    Double.toString(MIN_LON) + ", " +
                    Double.toString(MAX_LON) + "]");
        }

        int xyTilesCount = (int)Math.pow(2, z);
        int x = (int) Math.floor((lon + 180.0) / 360.0 * xyTilesCount);
        int y = (int) Math.floor((1.0 - Math.log(Math.tan(lat * Math.PI / 180.0) + 1.0 / Math.cos(lat * Math.PI / 180.0)) / Math.PI) / 2.0 * xyTilesCount);

        return Integer.toString(z) + "/" + Integer.toString(x) + "/" + Integer.toString(y);
    }



}
