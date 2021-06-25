package week03_bikesharing;

import java.time.LocalDateTime;
import java.util.Objects;

public class BikeRental {
    private String bikeId;
    private String userId;
    private LocalDateTime time;
    private double distance;

    public BikeRental(String bikeId, String userId, LocalDateTime time, double distance) {
        this.bikeId = bikeId;
        this.userId = userId;
        this.time = time;
        this.distance = distance;
    }

    public String getBikeId() {
        return bikeId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BikeRental that = (BikeRental) o;
        return Double.compare(that.distance, distance) == 0 && Objects.equals(bikeId, that.bikeId) && Objects.equals(userId, that.userId) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bikeId, userId, time, distance);
    }

    @Override
    public String toString() {
        return "BikeRental{" +
                "bikeId='" + bikeId + '\'' +
                ", userId='" + userId + '\'' +
                ", time=" + time +
                ", distance=" + distance +
                '}';
    }
}
