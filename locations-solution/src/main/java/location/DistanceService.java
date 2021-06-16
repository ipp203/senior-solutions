package location;

import java.util.Optional;

public class DistanceService {

    LocationRepository repo;

    public DistanceService(LocationRepository repo) {
        this.repo = repo;
    }

    public Optional<Double> calculateDistance(String name1, String name2) {
        Optional<Location> result1 = repo.findByName(name1);
        Optional<Location> result2 = repo.findByName(name2);
        if (result1.isEmpty() || result2.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(result1.get().distanceFrom(result2.get()));
        }
    }
}
