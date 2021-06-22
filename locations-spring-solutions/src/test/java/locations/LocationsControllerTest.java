package locations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationsControllerTest {
    @Mock
    LocationsService service;

    @InjectMocks
    LocationsController controller;

    @Test
    void getLocationsTest() {
        when(service.getLocations()).thenReturn(List.of(
                new Location(1L,"Eger",42.2,43.3),
                new Location(2L,"Pecs",41.1,41.1)));
        List<Location> locations = controller.getLocations();
        assertThat(locations)
                .hasSize(2)
                .extracting("name")
                .contains("Eger","Pecs");
        verify(service).getLocations();
    }

    @Test
    void getLocationsTestWithNull(){
        assertThat(controller.getLocations()).hasSize(0);
    }
}