package locations;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class LocationsServiceTest {

    Environment environment;

    @Test
    void getLocationsTest() {
        LocationsService service = new LocationsService(new ModelMapper(), environment);
        assertThat(service.getLocations())
                .extracting("name","lat","lon")
                .isNotNull()
                .contains(tuple("Budapest",43.0,42.0),
                        tuple("Debrecen",43.0,44.0));
    }
}