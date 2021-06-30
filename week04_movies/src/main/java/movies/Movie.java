package movies;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        rateAverage = rates.stream().collect(Collectors.summarizingInt(Integer::intValue)).getAverage();
    }
}
