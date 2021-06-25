package week03_bikesharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Week03BikesharingApplication {

    public static void main(String[] args) {
        SpringApplication.run(Week03BikesharingApplication.class, args);
        BikeService service = new BikeService();
        List<BikeRental> result = service.getRentalList();
        System.out.println(result);
    }

}
