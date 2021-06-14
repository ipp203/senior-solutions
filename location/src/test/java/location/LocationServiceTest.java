package location;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class LocationServiceTest {

    @TempDir
    public File folder;

    @Test
    void testWriteLocations() throws IOException {
        Path path = new File(folder,"locations.csv").toPath();
        List<Location> locations = List.of(
                new Location("Budapest", 47.497912, 19.040235),
                new Location("Greenwich", 51.46, 0),
                new Location("Quitu", 0, 78.5)
        );
        new LocationService().writeLocations(path,locations);

        List<Location> result = Files.readAllLines(path, StandardCharsets.UTF_8).stream()
                .map(Location::parseLocation)
                .collect(Collectors.toList());
        assertArrayEquals(new String[]{"Budapest","Greenwich","Quitu"},
                result.stream().map(Location::getName).toArray());

    }

    @Test
    void readLocations() {
    }
}