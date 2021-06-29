package movies;

import lombok.Data;

@Data
public class MovieDto {
    private long id;
    private String title;
    private int length;
    private double rateAverage;
}
