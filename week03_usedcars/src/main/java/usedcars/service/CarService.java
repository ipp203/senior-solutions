package usedcars.service;

import org.springframework.stereotype.Service;
import usedcars.model.Car;
import usedcars.model.KmState;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    private List<Car> cars;

    public CarService() {
        cars = List.of(
                new Car("Opel", "Astra", 16, Car.Status.NORMAL, List.of(
                        new KmState(LocalDate.of(2005, 1, 1), 0),
                        new KmState(LocalDate.of(2010, 1, 1), 50_000),
                        new KmState(LocalDate.of(2015, 1, 1), 100_000),
                        new KmState(LocalDate.of(2020, 1, 1), 150_000)
                )),
                new Car("Opel", "Corsa", 3, Car.Status.GREAT, List.of(
                        new KmState(LocalDate.of(2018, 1, 1), 20_000),
                        new KmState(LocalDate.of(2019, 1, 1), 60_000),
                        new KmState(LocalDate.of(2020, 1, 1), 100_000)
                ))
                ,
                new Car("Toyota", "Yaris", 1, Car.Status.GREAT, List.of(
                        new KmState(LocalDate.of(2021, 1, 1), 10_000)
                )),
                new Car("Lada", "Samara", 33, Car.Status.BAD, List.of(
                        new KmState(LocalDate.of(1990, 1, 1), 10_000),
                        new KmState(LocalDate.of(2010, 1, 1), 260_000),
                        new KmState(LocalDate.of(2020, 1, 1), 350_000)
                ))
        );
    }

    public List<Car> getCars() {
        return List.copyOf(cars);
    }

    public List<String> getTypes() {
        return cars.stream()
                .map(c -> c.getBrand() + " " +c.getType())
                .distinct()
                .collect(Collectors.toList());
    }
}
