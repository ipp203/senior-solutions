package locations;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerRestTemplateIT {
    @Autowired
    TestRestTemplate template;


    @Test
    @Order(2)
    void testUpdateLocation() {

        LocationDto result = template
                .exchange("/locations/1",
                        HttpMethod.PUT,
                        new HttpEntity<>(new UpdateLocationCommand("Eger", 42.1, 42.1)),
                        LocationDto.class).getBody();

        assertEquals("Eger", result.getName());
    }

    @Test
    @Order(1)
    void testGetLocations() {

        List<LocationDto> result = template
                .exchange("/locations",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<LocationDto>>() {})
                .getBody();

        assertThat(result)
                .extracting("name")
                .containsAll(List.of("Debrecen","Budapest"));
    }
}
