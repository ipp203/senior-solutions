package locations;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LocationsController {
    private List<Location> locations;

    public LocationsController() {
        this.locations = List.of(
                new Location(1L,"Budapest",43.0,42.0),
                new Location(2L,"Debrecen",43.0,44.0));
    }

    @GetMapping("/locations")
    @ResponseBody
    public String getLocations(){
        return locations.stream()
                .map(Location::getName)
                .collect(Collectors.joining(", "));
    }

}
