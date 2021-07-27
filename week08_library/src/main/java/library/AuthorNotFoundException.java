package library;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class AuthorNotFoundException extends AbstractThrowableProblem {

    public AuthorNotFoundException(long id) {
        super(URI.create("authors/authors-notfound"),
                "Author not found",
                Status.NOT_FOUND,
                "Author not found, id: " + id);
    }
}
