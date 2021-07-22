package activitytracker;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "act_id_gen")
    @TableGenerator(name = "act_id_gen", pkColumnName = "id_gen", valueColumnName = "id_val", allocationSize = 5, initialValue = 1000)
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false, length = 200)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Type type;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ElementCollection
    @CollectionTable(name = "activity_labels", joinColumns = @JoinColumn(name = "act_id"))
    @Column(name = "label")
    @OrderBy
    private List<String> labels;

    @OneToMany(mappedBy = "activity", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @OrderBy(value = "time")
    private List<TrackPoint> trackPoints;

    @ManyToMany
    @OrderBy("name")
    private List<Area> areas;

    public Activity(LocalDateTime startTime, String description, Type type) {
        this.startTime = startTime;
        this.description = description;
        this.type = type;
    }

    public void addTrackPoint(TrackPoint trackPoint) {
        trackPoint.setActivity(this);
        if (trackPoints == null) {
            trackPoints = new ArrayList<>();
        }
        trackPoints.add(trackPoint);
    }

    public void addArea(Area area) {
        area.addActivity(this);
        if (areas == null) {
            areas = new ArrayList<>();
        }
        areas.add(area);
    }

    @PrePersist
    public void savePersistTime() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void saveUpdateTime() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id) && Objects.equals(description, activity.description) && type == activity.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, type);
    }
}
