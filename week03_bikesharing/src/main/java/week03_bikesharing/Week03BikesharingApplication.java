package week03_bikesharing;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class Week03BikesharingApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =SpringApplication.run(Week03BikesharingApplication.class, args);
//        BikeService bs = (BikeService) context.getBean(BikeService.class);
//        BikeService service = new BikeService();
//        List<BikeRental> result = bs.getRentalList();
//        System.out.println(result);
    }

}
