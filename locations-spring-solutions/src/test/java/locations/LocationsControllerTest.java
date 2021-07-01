package locations;

import net.bytebuddy.description.method.MethodDescription;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
        List<Location> list = List.of(
                new Location(1L, "Eger", 42.2, 43.3),
                new Location(2L, "Pecs", 41.1, 41.1));

        ModelMapper modelMapper = new ModelMapper();
        Type targetListType = new TypeToken<List<Location>>(){}.getType();
        List<LocationDto> result = modelMapper.map(list,targetListType);

        when(service.getLocationByNameFragment(any())).thenReturn(result);
        List<LocationDto> locations = controller.getLocationByName(Optional.empty());
        assertThat(locations)
                .hasSize(2)
                .extracting("name")
                .contains("Eger", "Pecs");
        verify(service).getLocationByNameFragment(any());
    }

    @Test
    void getLocationsTestWithNull() {
        assertThat(controller.getLocationByName(Optional.empty())).hasSize(0);
    }
}