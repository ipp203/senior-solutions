package locations;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class LocationsServiceTest {

    @Test
    void getLocationsTest() {
        LocationsService service = new LocationsService();
        assertThat(service.getLocations())
                .extracting("name","lat","lon")
                .isNotNull()
                .contains(tuple("Budapest",43.0,42.0),
                        tuple("Debrecen",43.0,44.0));
    }
}