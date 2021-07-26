package movies;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class MovieService {

    private MovieRepository repository;
    private ModelMapper modelMapper;

    public MovieService(MovieRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public List<MovieDto> getMovies() {
        List<Movie> result = repository.findAll();
        Type targetListType = new TypeToken<List<Movie>>() {
        }.getType();

        return modelMapper.map(result, targetListType);
    }

    @Transactional
    public MovieDto createMovie(CreateMovieCommand command) {
        Movie movie = new Movie(command.getTitle());
        repository.save(movie);
        return modelMapper.map(movie, MovieDto.class);
    }


    @Transactional
    public MovieDto addRating(long id, AddRatingCommand command) {
        Movie movie = repository.findById(id).orElseThrow(()->new MovieNotFoundException(id));
        movie.addRating(command.getRate());
        return modelMapper.map(movie, MovieDto.class);
    }
}
