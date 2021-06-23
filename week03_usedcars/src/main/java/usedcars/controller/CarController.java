package usedcars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import usedcars.service.CarService;
import usedcars.model.Car;

import java.util.List;

@Controller
public class CarController {
    private CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping("/cars")
    @ResponseBody
    public List<Car> getCars() {
        return service.getCars();
    }

    @GetMapping("/types")
    @ResponseBody
    public List<String> getTypes() {
        return service.getTypes();
    }
}
