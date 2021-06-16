package location;

public class LocationParser {
    public Location parse(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Argument is null or empty.");
        }

        String[] data = text.split(",");
        if (data.length == 3) {
            String name = data[0];
            double lat;
            double lon;
            try {
                lat = Double.parseDouble(data[1]);
                lon = Double.parseDouble(data[2]);
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Bad number format", nfe);
            }
            return new Location(name, lat, lon);
        }
        throw new IllegalArgumentException("Wrong argument");
    }

    public boolean isOnEquator(Location location) {
        return location.getLat() == 0;
    }

    public boolean isOnPrimeMeridian(Location location) {
        return location.getLon() == 0;
    }


}
