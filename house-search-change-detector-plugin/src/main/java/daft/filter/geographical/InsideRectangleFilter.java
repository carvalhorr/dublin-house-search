package daft.filter.geographical;

import java.util.Arrays;
import java.util.Map;

public class InsideRectangleFilter extends GeographicalFilter {

    protected double[] latitudes;
    protected double[] longitudes;

    public InsideRectangleFilter(GeographicalPoint point1, GeographicalPoint point2) {
        latitudes = new double[]{point1.getLatitude(), point2.getLatitude()};
        longitudes = new double[]{point1.getLongitude(), point2.getLongitude()};
        Arrays.sort(latitudes);
        Arrays.sort(longitudes);
    }

    public InsideRectangleFilter(double latitude1, double longitude1, double latitude2, double longitude2) {
        this(new GeographicalPoint(latitude1, longitude1),
                new GeographicalPoint(latitude2, longitude2));
    }

    public InsideRectangleFilter(String latitude1, String longitude1, String latitude2, String longitude2) {
        this(new GeographicalPoint(latitude1, longitude1),
                new GeographicalPoint(latitude2, longitude2));
    }

    @Override
    public boolean apply(Map<String, String> fields) {
        double latitude = Double.parseDouble(fields.get(LATITUDE_FIELD));
        double longitude = Double.parseDouble(fields.get(LONGITUDE_FIELD));
        return getSmallerLatitude() <= latitude
                && getBiggerLatitude() >= latitude
                && getSmallerLongitude() <= longitude
                && getBiggerLongitude() >= longitude;
    }

    private double getSmallerLatitude() {
        return latitudes[0];
    }

    private double getSmallerLongitude() {
        return longitudes[0];
    }

    private double getBiggerLatitude() {
        return latitudes[1];
    }

    private double getBiggerLongitude() {
        return longitudes[1];
    }

}
