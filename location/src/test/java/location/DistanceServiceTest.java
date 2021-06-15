package location;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DistanceServiceTest {

    @Mock
    LocationRepository repo;

    @InjectMocks
    DistanceService service;

    @Test
    void testCalculateDistance() {
        LocationRepository localRepo = mock(LocationRepository.class);
        DistanceService localService = new DistanceService(localRepo);
        Optional<Double> result = localService.calculateDistance("Budapest", "Prague");
        assertEquals(Optional.empty(), result);
    }


    @Test
    void testCalculateDistanceWithAnnotations() {
        Optional<Double> result = service.calculateDistance("Budapest", "Prague");
        assertEquals(Optional.empty(), result);
    }

    @Test
    void testCalculateDistanceResult() {
        when(repo.findByName("Budapest")).thenReturn(Optional.of(new Location("", 47.497912, 19.040235)));
        when(repo.findByName("Greenwich")).thenReturn(Optional.of(new Location("", 51.46, 0)));

        assertEquals(1439.58, service.calculateDistance("Budapest", "Greenwich").get(), 0.05);

        verify(repo).findByName("Budapest"); //meg volt hivva igy
        verify(repo, never()).findByName("");

        verify(repo).findByName(argThat(s -> s.equals("Budapest"))); //meg volt hivva ezzel az argumentumal
    }

}