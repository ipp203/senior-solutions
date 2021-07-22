package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LocationsRepositoryIT {
    @Autowired
    LocationsRepository repository;

    @Test
    void testSaveAndFindById(){
        Location location = new Location("Eger",40.4,41.41);
        repository.save(location);
        Optional<Location> loadedLocation = repository.findById(location.getId());
        if(loadedLocation.isEmpty()){
            fail();
        }

        assertEquals("Eger",loadedLocation.get().getName());
    }
    @Test
    void testSaveAndLisAll(){
        repository.save(new Location("Eger",40.4,41.41));
        repository.save(new Location("Debrecen",40.4,41.41));
        repository.save(new Location("Tihany",40.4,41.41));

        List<Location> locations = repository.findAll();

        assertEquals(3,locations.size());
    }
}