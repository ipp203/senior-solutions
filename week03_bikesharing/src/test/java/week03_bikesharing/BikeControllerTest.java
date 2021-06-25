package week03_bikesharing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BikeControllerTest {
    @Mock
    BikeService service;

    @InjectMocks
    BikeController controller;

    private List<BikeRental> list;
    @BeforeEach
    void init() {
        list = List.of(
                new BikeRental("aaa", "bbb", LocalDateTime.of(2021, 6, 25, 16, 41, 0), 12.2),
                new BikeRental("ccc", "ddd", LocalDateTime.of(2021, 6, 25, 16, 42, 20), 1.2)
        );
    }


    @Test
    void getBikeRentalList() {

        when(service.getRentalList()).thenReturn(list);
        assertThat(controller.getBikeRentalList())
        .hasSize(2)
        .contains(list.get(0));

        verify(service).getRentalList();
    }

    @Test
    void getUsers() {
        List<String> users = list.stream().map(BikeRental::getUserId).collect(Collectors.toList());

        when(service.getUsers()).thenReturn(users);
        assertThat(controller.getUsers())
                .hasSize(2)
                .contains(users.get(0));

        verify(service).getUsers();
    }
}