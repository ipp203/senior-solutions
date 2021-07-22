package activitytracker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQuery(name = "findTrackPointCoordinatesByDate",
        query = "select new activitytracker.Coordinate(t.lat,t.lon) from TrackPoint t where t.activity.startTime > :time")
public class TrackPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate time;
    private double lat;
    private double lon;

    @ManyToOne
    private Activity activity;

    public TrackPoint(LocalDate time, double lat, double lon) {
        this.time = time;
        this.lat = lat;
        this.lon = lon;
    }


}
