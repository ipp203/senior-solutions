package location;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocationService {
    public void writeLocations(Path file, List<Location> locations) {
        try (BufferedWriter bw = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            for (Location loc : locations) {
                bw.write(loc.getName() + "," + loc.getLat() + "," + loc.getLon());
                bw.newLine();
            }
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Can not create file!", ioe);
        }
    }

    public List<Location> readLocations(Path file) {
        try (Stream<String> stream = Files.newBufferedReader(file, StandardCharsets.UTF_8).lines()) {
            return stream
                    .map(Location::parseLocation)
                    .collect(Collectors.toList());
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Can not read file!", ioe);
        }
    }


}
