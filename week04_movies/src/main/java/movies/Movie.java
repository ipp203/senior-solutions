package movies;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Movie {
    private long id;
    private String title;
    private int length;
    private List<Integer> rates = new ArrayList<>();
    private double rateAverage;

    public Movie(long id, String title, int length) {
        this.id = id;
        this.title = title;
        this.length = length;
    }

    public void addRate(int rate) {
        rates.add(rate);
        rateAverage = 0;
        for (double r : rates) {
            rateAverage += r;
        }
        rateAverage /= rates.size();
    }
}
