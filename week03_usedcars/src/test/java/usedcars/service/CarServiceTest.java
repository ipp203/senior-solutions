package usedcars.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usedcars.service.CarService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class CarServiceTest {
    CarService service;

    @BeforeEach
    void setUp() {
        service=new CarService();
    }

    @Test
    void getCars() {
        assertThat(service.getCars())
                .hasSize(4)
                .extracting("brand","type")
                .contains(tuple("Opel","Astra"),tuple("Toyota","Yaris"));
    }

    @Test
    void getTypes() {
        assertThat(service.getTypes())
                .hasSize(4)
                .contains("Opel Astra","Lada Samara");
    }
}