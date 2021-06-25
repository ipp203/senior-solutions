package usedcars.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
class CarControllerIT {

    @Autowired
    CarController controller;

    @Test
    void getCars() {
        assertThat(controller.getCars())
                .hasSize(4)
                .extracting("brand","type")
                .contains(tuple("Opel","Astra"),
                        tuple("Toyota","Yaris"));
    }

    @Test
    void getTypes() {
        assertThat(controller.getTypes())
                .hasSize(4)
                .contains("Opel Astra","Lada Samara");
    }
}