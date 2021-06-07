package location;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class LocationOperatorsTest {
    @Test
    void testFilterOnNorth() {
        List<Location> locations = Arrays.asList(
                new Location("Budapest", 47.497912, 19.040235),
                new Location("Greenwich", 51.46, 0),
                new Location("Quitu", 0, 78.5));
        assertArrayEquals(new String[]{"Budapest", "Greenwich", "Quitu"},
                locations.stream().map(Location::getName).toArray());
    }
}