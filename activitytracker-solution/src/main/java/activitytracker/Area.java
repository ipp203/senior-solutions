package activitytracker;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany(mappedBy = "areas")
    private List<Activity> activities;

    public Area(String name) {
        this.name = name;
    }

    public void addActivity(Activity activity) {
        if (activities == null) {
            activities = new ArrayList<>();
        }
        activities.add(activity);
    }
}
