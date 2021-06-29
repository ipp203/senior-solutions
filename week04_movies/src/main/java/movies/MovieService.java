package movies;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private List<Movie> movies = new ArrayList<>();
    private AtomicLong id = new AtomicLong();

    private ModelMapper modelMapper;

    public MovieService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<MovieDto> getMoviesByTitle(Optional<String> title) {
        Type targetListType = new TypeToken<List<MovieDto>>(){}.getType();

        List<Movie> result = movies.stream()
                .filter(m-> title.isEmpty() || m.getTitle().toLowerCase().equals(title.get().toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());

        return modelMapper.map(result,targetListType);
    }

    public MovieDto getMovieById(long id) {
        Movie result = movies.stream()
                .filter(m->m.getId() == id)
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Movie not found, id: " + id));

        return modelMapper.map(result,MovieDto.class);
    }

    public MovieDto createMovie(CreateMovieCommand command) {
        Movie movie = new Movie(id.incrementAndGet(),command.getTitle(),command.getLength());
        movies.add(movie);

        return modelMapper.map(movie, MovieDto.class);
    }

    public MovieDto addRateById(long id, AddRateCommand rate) {
        Movie movie = movies.stream()
                .filter(m->m.getId() == id)
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Movie not found, id: " + id));

        movie.addRate(rate.getRate());

        return modelMapper.map(movie,MovieDto.class);
    }

    public void deleteMovieById(long id) {
        Movie movie = movies.stream()
                .filter(m->m.getId() == id)
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Movie not found, id: " + id));
        movies.remove(movie);
    }
}
