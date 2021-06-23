package usedcars.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import usedcars.model.Car;
import usedcars.model.KmState;
import usedcars.service.CarService;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    CarService service;
    @InjectMocks
    CarController controller;


    @Test
    void getCarsTestWithEmptyList() {
        List<Car> cars = controller.getCars();
        assertThat(cars).hasSize(0);
    }

    @Test
    void getCarsTest() {
        when(service.getCars()).thenReturn(List.of(
                new Car("Opel", "Insignia", 3, Car.Status.GREAT,
                        List.of(new KmState(LocalDate.of(2019, 1, 1), 15000),
                                new KmState(LocalDate.of(2021, 1, 1), 30000)))));

        List<Car> cars = controller.getCars();
        assertThat(cars)
                .hasSize(1)
                .extracting("brand")
                .contains("Opel");

        verify(service).getCars();
    }

    @Test
    void getTypesTestWithEmptyList() {
        assertThat(service.getTypes()).hasSize(0);
    }

    @Test
    void getTypesTest(){
        when(service.getTypes()).thenReturn(List.of(
                "Opel Insignia","Toyota Corolla"));

        List<String> types = controller.getTypes();
        assertThat(types)
                .hasSize(2)
                .contains("Opel Insignia");

        verify(service).getTypes();
    }
}