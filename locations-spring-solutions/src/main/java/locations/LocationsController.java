package locations;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@Controller
public class LocationsController {
    private LocationsService service;

    public LocationsController(LocationsService service) {
        this.service = service;
    }

    @GetMapping("/locations")
    @ResponseBody
    public String getLocations(){
        return service.getLocations().stream()
                .map(Location::getName)
                .collect(Collectors.joining(", "));
    }

}
