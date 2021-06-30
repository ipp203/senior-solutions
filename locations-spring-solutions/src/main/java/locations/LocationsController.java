package locations;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto createLocation(@RequestBody CreateLocationCommand command){
        return service.createLocation(command);
    }

    @PutMapping("/locations/{id}")
    public LocationDto updateLocation(@PathVariable("id") long id,@RequestBody UpdateLocationCommand command){
        return service.updateLocation(id,command);
    }

    @DeleteMapping("/locations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable("id") long id){
        service.deleteLocation(id);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> locationNotFound(LocationNotFoundException exception){
        Problem problem = Problem.builder()
                .withType(URI.create("locations/location-not-found"))
                .withTitle("Not found")
                .withStatus(Status.NOT_FOUND)
                .withDetail(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

}
