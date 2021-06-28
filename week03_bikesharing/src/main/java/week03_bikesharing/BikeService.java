package week03_bikesharing;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BikeService {
    //private static final String filename = "f:/Training360/prg_halado/senior-solutions/week03_bikesharing/bikes.csv";
    private String filename = "bikes.csv";

    private List<BikeRental> rentalList;


    private void loadDataFromFile() {
        Path path = Path.of(filename);
        System.out.println(path);
        try (Stream<String> stream = Files.lines(path)) {
            rentalList = stream
                    .map(this::parse)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not read file", ioe);
        }
    }

    private BikeRental parse(String str) {
        String[] data = str.split(";");
        if (data.length == 4) {
            String bikeId = data[0];
            String userId = data[1];
            LocalDateTime time;
            try {
                time = LocalDateTime.parse(data[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            } catch (DateTimeParseException dtpe) {
                return null;
            }
            double distance = Double.parseDouble(data[3]);
            return new BikeRental(bikeId, userId, time, distance);
        } else {
            return null;
        }
    }

    public List<BikeRental> getRentalList() {
        if(rentalList == null || rentalList.isEmpty()){
            loadDataFromFile();
        }
        return new ArrayList<>(rentalList);
    }
    public List<String> getUsers() {
        if(rentalList == null || rentalList.isEmpty()){
            loadDataFromFile();
        }
        return rentalList.stream()
                .map(BikeRental::getUserId)
                .collect(Collectors.toList());
    }
}
