package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from location")
public class LocationsControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    @Autowired
    LocationsService service;

    @Autowired
    EntityManager em;

    @Test
    void testListLocations() {
        template.postForObject("/locations",
                new CreateLocationCommand("Eger", 41.41, 42.42),
                LocationDto.class);
        template.postForObject("/locations",
                new CreateLocationCommand("Miskolc", 41.41, 42.42),
                LocationDto.class);

        List<LocationDto> locations = template.exchange("/locations",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LocationDto>>() {
                }).getBody();

        assertThat(locations)
                .extracting("name")
                .hasSize(2)
                .contains("EGER")
                .contains("MISKOLC");
    }

    @Test
    void testSaveAndGetByName() {
        template.postForObject("/locations",
                new CreateLocationCommand("Eger", 41.41, 42.42),
                LocationDto.class);

        List<LocationDto> locationDtos = template
                .exchange("/locations?nameFragment=ger",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<LocationDto>>() {
                        })
                .getBody();

        assertThat(locationDtos)
                .hasSize(1)
                .extracting(LocationDto::getName)
                .containsExactly("EGER");
    }

    @Test
    void testSaveAndUpdate() {
        template.postForObject("/locations",
                new CreateLocationCommand("Eger", 41.41, 42.42),
                LocationDto.class);

        Location location = em
                .createQuery("select l from Location l where l.name='EGER'", Location.class)
                .getSingleResult();

        template.put("/locations/" + location.getId(), new UpdateLocationCommand(location.getName(), 40.0, 40.0));

        LocationDto locationDto = template.getForObject("/locations/" + location.getId(), LocationDto.class);

        assertEquals("EGER", locationDto.getName());
        assertEquals(40.0, locationDto.getLon());

    }
}
