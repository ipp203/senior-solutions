package week03_bikesharing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class BikeControllerIT {

    @Autowired
    BikeController controller;

    @Test
    void getBikeRentalList() {


        assertThat(controller.getBikeRentalList())
                .hasSize(5);
                //.contains(list.get(0));


    }

    @Test
    void getUsers() {

        assertThat(controller.getUsers())
                .hasSize(5);
                //.contains(users.get(0));


    }

}
