package locations;

import java.util.ArrayList;
import java.util.List;


public class LocationsService {
    private List<Location> locations;

    public LocationsService() {
        this.locations = List.of(
                new Location(1L,"Budapest",43.0,42.0),
                new Location(2L,"Debrecen",43.0,44.0));
    }

    public List<Location> getLocations() {
        return new ArrayList<>(locations);
    }
}
