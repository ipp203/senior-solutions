package locations;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
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
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto createLocation(@Valid @RequestBody CreateLocationCommand command){
        return service.createLocation(command);
    }

    @PutMapping("/locations/{id}")
    public LocationDto updateLocation(@PathVariable("id") long id,@Valid @RequestBody UpdateLocationCommand command){
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleValidationError(MethodArgumentNotValidException e){
        List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(fe->new Violation(fe.getField(),fe.getDefaultMessage()))
                .collect(Collectors.toList());

        Problem problem = Problem.builder()
                .withType(URI.create("Location/not-valid"))
                .withTitle("Validation error")
                .withStatus(Status.BAD_REQUEST)
                .withDetail(e.getMessage())
                .with("Violations",violations)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

}
