package week03_bikesharing;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


class BikeServiceTest {

    private BikeService service;

    @BeforeEach
    void setUp() {
        service = new BikeService();
    }

    @ExtendWith(SoftAssertionsExtension.class)
    @Test
    void getRentalList(SoftAssertions softly) {
        List<BikeRental> result = service.getRentalList();
        System.out.println(result);
        softly.assertThat(result)
                .hasSize(5)
                .extracting("time", "distance")
                .contains(tuple(LocalDateTime.of(2021, 6, 24, 8, 53, 21), 2.9));

    }

    @Test
    void getUserList() {
        List<String> result = service.getUsers();
        assertThat(result)
                .hasSize(5)
                .contains("US336");
    }
}