package movies;

import lombok.Data;

@Data
public class CreateMovieCommand {
    private String title;
    private int length;
}
