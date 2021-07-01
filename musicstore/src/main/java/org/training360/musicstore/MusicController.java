package org.training360.musicstore;

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
@RequestMapping("/api/instruments")
public class MusicController {
    private final MusicStoreService service;

    public MusicController(MusicStoreService service) {
        this.service = service;
    }

    @GetMapping
    public List<InstrumentDTO> filterInstruments(@RequestParam Optional<String> brand, @RequestParam Optional<Integer> price) {
        return service.filterInstruments(brand, price);
    }

    @PostMapping
    public InstrumentDTO createInstrument(@Valid @RequestBody CreateInstrumentCommand command) {
        return service.createInstrument(command);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInstruments() {
        service.deleteAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInstrument(@PathVariable("id") long id) {
        service.deleteById(id);
    }

    @GetMapping("/{id}")
    public InstrumentDTO getInstrumentById(@PathVariable("id") long id) {
        return service.getInstrumentById(id);
    }

    @PutMapping("/{id}")
    public InstrumentDTO updatePriceById(@PathVariable("id") long id, @Valid @RequestBody UpdatePriceCommand command) {
        return service.updatePriceById(id, command);
    }

    @ExceptionHandler(InstrumentNotFoundException.class)
    public ResponseEntity<Problem> handleNotFoundException(InstrumentNotFoundException exception) {
        Problem problem = Problem.builder()
                .withType(URI.create("instruments/not-found"))
                .withTitle("Not found")
                .withStatus(Status.NOT_FOUND)
                .withDetail(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleNotValidArgumentError(MethodArgumentNotValidException exception) {
        List<Violation> violations = exception.getBindingResult().getFieldErrors().stream()
                .map(fe -> new Violation(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        Problem problem = Problem.builder()
                .withType(URI.create("instruments/not-valid"))
                .withTitle("Validation error")
                .withStatus(Status.BAD_REQUEST)
                .withDetail(exception.getMessage())
                .with("Violations", violations)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}
