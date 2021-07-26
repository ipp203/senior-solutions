package movies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ElementCollection
    private List<Integer> ratings;

    public Movie(String title) {
        this.title = title;
    }

    public void addRating(int rating){
        if(ratings == null){
            ratings = new ArrayList<>();
        }
        ratings.add(rating);
    }
}
