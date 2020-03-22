package hu.jtechlog.cities;

import org.springframework.stereotype.Component;

@Component
public class HaversineCalculator {

    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

    public double calculateDistance(double startLat, double startLong,
                                    double endLat, double endLong) {

        double distanceLat  = Math.toRadians(endLat - startLat);
        double distanceLong = Math.toRadians(endLong - startLong);

        startLat = Math.toRadians(startLat);
        endLat   = Math.toRadians(endLat);

        double a = doHaversine(distanceLat) + Math.cos(startLat) * Math.cos(endLat) * doHaversine(distanceLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // <-- d
    }

    private double doHaversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
