package hu.jtechlog.cities;

public class Haversine {

    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

    public static double distance(double startLat, double startLong,
                                  double endLat, double endLong) {

        double distanceLat  = Math.toRadians(endLat - startLat);
        double distanceLong = Math.toRadians(endLong - startLong);

        startLat = Math.toRadians(startLat);
        endLat   = Math.toRadians(endLat);

        double a = haversin(distanceLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(distanceLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // <-- d
    }

    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
