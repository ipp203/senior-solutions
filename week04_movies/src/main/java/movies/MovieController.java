package movies;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/movies")
public class MovieController {
    private MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public List<MovieDto> getMoviesByTitle(@RequestParam Optional<String> title){
        return service.getMoviesByTitle(title);
    }

    @GetMapping("/{id}")
    public MovieDto getMovieById(@PathVariable("id") long id){
        return service.getMovieById(id);
    }

    @PostMapping
    public MovieDto createMovie(@RequestBody CreateMovieCommand command){
        return service.createMovie(command);
    }

    @PostMapping("/{id}/rating")
    public MovieDto addRateById(@PathVariable("id") long id,@RequestBody AddRateCommand command){
        return service.addRateById(id,command);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable("id") long id){
        service.deleteMovieById(id);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void handleNotFound(){}
}
