package fleamarket;

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

@RequestMapping("/fleamarket/advertisement")
@RestController
public class FleamarketControl {
    private final FleamarketService service;

    public FleamarketControl(FleamarketService service) {
        this.service = service;
    }

    @DeleteMapping("/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        service.deleteAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdById(@PathVariable("id") long id) {
        service.deleteById(id);
    }

    @DeleteMapping
    public void deleteOldestByCategory(@RequestParam Optional<String> category) {
        service.deleteOldestByCategory(category);
    }

    @PostMapping
    public AdvertisementDTO createAd(@Valid @RequestBody CreateAdvertisementCommand command) {
        return service.createAd(command);
    }

    @GetMapping
    public List<AdvertisementDTO> getAds(@RequestParam Optional<String> category, @RequestParam Optional<String> word) {
        return service.getAds(category, word);
    }

    @GetMapping("/{id}")
    public AdvertisementDTO getAdById(@PathVariable("id") long id) {
        return service.getAdById(id);
    }

    @PutMapping("/{id}")
    public AdvertisementDTO updateAd(@PathVariable("id") long id, @Valid @RequestBody UpdateAdvertisementCommand command) {
        return service.updateAd(id, command);
    }

    @ExceptionHandler(AdvertisementNotFoundException.class)
    public ResponseEntity<Problem> handleNotFoundException(AdvertisementNotFoundException exception) {
        Problem problem = Problem.builder()
                .withType(URI.create("fleamarket/advertisement-not-found"))
                .withTitle("Not found")
                .withStatus(Status.NOT_FOUND)
                .withDetail(exception.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleNotValidException(MethodArgumentNotValidException exception) {
        List<Violation> violations = exception.getBindingResult().getFieldErrors().stream()
                .map(fe -> new Violation(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        Problem problem = Problem.builder()
                .withType(URI.create("fleamarket/not-valid"))
                .withTitle("Validation error")
                .withStatus(Status.BAD_REQUEST)
                .withDetail(exception.getMessage())
                .with("Violations", violations)
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

}
