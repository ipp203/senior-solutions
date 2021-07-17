package activitytracker;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    private String description;

    @Enumerated(EnumType.STRING)
    private Type type;

    public Activity(LocalDateTime startTime, String description, Type type) {
        this.startTime = startTime;
        this.description = description;
        this.type = type;
    }
}
