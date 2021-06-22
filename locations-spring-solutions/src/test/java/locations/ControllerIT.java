package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ControllerIT {
    @Autowired
    LocationsController controller;

    @Test
    void getLocationsTest(){
        List<Location> locations = controller.getLocations();
        assertThat(locations)
                .hasSize(2)
                .extracting("name")
                .contains("Budapest","Debrecen");
    }
}
