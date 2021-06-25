package week03_bikesharing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BikeController {

    private final BikeService service;

    public BikeController(BikeService service) {
        this.service = service;
    }

    @GetMapping("/history")
    public List<BikeRental> getBikeRentalList() {
        return service.getRentalList();
    }

    @GetMapping("/users")
    public List<String> getUsers() {
        return service.getUsers();

    }
}
