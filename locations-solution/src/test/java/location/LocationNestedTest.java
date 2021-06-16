package location;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocationNestedTest {

    private LocationParser locationParser;

    @BeforeEach
    void init() {
        locationParser = new LocationParser();
    }

    @Nested
    class InnerClass1 {
        private Location favoriteLocation;

        @BeforeEach
        void init() {
            favoriteLocation = new Location("Budapest", 47.497912, 19.040235);
        }

        @Test
        void testIsOnEquator() {
            boolean result = locationParser.isOnEquator(favoriteLocation);
            assertFalse(result);
        }

        @Test
        void testIsOnPrimeMeridian() {
            boolean result = locationParser.isOnPrimeMeridian(favoriteLocation);
            assertFalse(result);
        }
    }

    @Nested
    class InnerClass2 {
        private Location favoriteLocation;

        @BeforeEach
        void init() {
            favoriteLocation = new Location("Dummy", 0.0, 0.0);
        }

        @Test
        void testIsOnEquator() {
            boolean result = locationParser.isOnEquator(favoriteLocation);
            assertTrue(result);
        }

        @Test
        void testIsOnPrimeMeridian() {
            boolean result = locationParser.isOnPrimeMeridian(favoriteLocation);
            assertTrue(result);
        }

    }
}
