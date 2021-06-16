package location;

public class Location {
    private String name;
    private double lat;
    private double lon;

    public Location(String name, double lat, double lon) {
        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("Lat must be between [-90;90]");
        }
        if (lon < -180 || lon > 180) {
            throw new IllegalArgumentException("Lon must be between [-180;180]");
        }
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double distanceFrom(Location location) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(location.lat - lat);
        double lonDistance = Math.toRadians(location.lon - lon);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(location.lat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // convert to kmeters

        //double height = el1 - el2;

        distance = Math.pow(distance, 2);// + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    public static Location parseLocation(String str) {
        String[] data = str.split(",");
        return new Location(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
    }
}
