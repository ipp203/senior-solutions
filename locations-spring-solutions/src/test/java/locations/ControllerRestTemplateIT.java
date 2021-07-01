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
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
                .containsAll(List.of("Debrecen", "Budapest"));
    }

    @Test
    @Order(3)
    void testCreateValidationWithWrongLatitiude() {
         Problem problem= template.postForObject(
                "/locations",
                new CreateLocationCommand("Proba", -100.0, 50.0),
                Problem.class);

         assertEquals(Status.BAD_REQUEST,problem.getStatus());
         assertTrue(problem.getDetail().contains("Latitude must be between -90 and 90!"));
    }

    @Test
    @Order(4)
    void testUpdateValidationWithWrongName() {
        Problem problem= template.postForObject(
                "/locations",
                new UpdateLocationCommand("  ", -10.0, 50.0),
                Problem.class);

        assertEquals(Status.BAD_REQUEST,problem.getStatus());
    }

}
