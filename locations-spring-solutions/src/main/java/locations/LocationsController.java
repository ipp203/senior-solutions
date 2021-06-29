package locations;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class LocationsController {
    private LocationsService service;

    public LocationsController(LocationsService service) {
        this.service = service;
    }

//    @GetMapping("/locations")
//    public List<LocationDto> getLocations() {
//        return service.getLocations();
//    }


    @GetMapping("/locations")
    public List<LocationDto> getLocationByName(@RequestParam Optional<String> nameFragment) {
        return service.getLocationByNameFragment(nameFragment);
    }

    @GetMapping("/locations/{id}")
    public LocationDto getLocationById(@PathVariable("id") long id) {
        return service.getLocationById(id);
    }

    @PostMapping("/locations")
    public LocationDto createLocation(@RequestBody CreateLocationCommand command){
        return service.createLocation(command);
    }

    @PutMapping("/locations/{id}")
    public LocationDto updateLocation(@PathVariable("id") long id,@RequestBody LocationUpdateCommand command){
        return service.updateLocation(id,command);
    }

}
