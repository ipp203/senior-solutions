package library;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository repository;
    private final ModelMapper modelMapper;

    public AuthorService(AuthorRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public List<AuthorDto> getAuthors() {
        return repository.findAll().stream()
                .map(a -> modelMapper.map(a, AuthorDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public AuthorDto createAuthor(CreateAuthorCommand command) {
        Author author = new Author(command.getName());
        repository.save(author);
        return modelMapper.map(author, AuthorDto.class);
    }

    @Transactional
    public AuthorDto addBook(long id, AddBookCommand command) {
        Author author = repository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
        Book book = new Book(command.getISBN(), command.getTitle());
        author.addBook(book);
        return modelMapper.map(author, AuthorDto.class);
    }

    @Transactional
    public void deleteAuthor(long id) {
        Author author = repository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
        repository.delete(author);
    }
}
