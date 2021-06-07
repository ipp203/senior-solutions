package location;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    private LocationParser locationParser;

    @BeforeEach
    void init() {
        locationParser = new LocationParser();
    }

    @Test
    @DisplayName("Test parsing")
    void testParse() {
        Location location = new LocationParser().parse("Budapest,47.497912,19.040235");
        assertEquals("Budapest", location.getName());
        assertEquals(47.497912, location.getLat(), 0.0005);
        assertEquals(19.040235, location.getLon(), 0.0005);
    }

    @Test
    @DisplayName("Test pars with wrong parameter")
    void testParseWithNull() {
        assertThrows(IllegalArgumentException.class, () -> new LocationParser().parse(null));
        assertThrows(IllegalArgumentException.class, () -> new LocationParser().parse("Paris,aaa,000"));
    }

    @Test
    @DisplayName("Is location on Equator")
    void testIsOnEquator() {
        assertTrue(locationParser.isOnEquator(new Location("Quitu", 0, 78.5)));
        assertFalse(locationParser.isOnEquator(new Location("Budapest", 47.497912, 19.040235)));
    }

    @Test
    @DisplayName("Is location on Prime Meridian")
    void testIsOnPrimeMeridian() {
        assertTrue(locationParser.isOnPrimeMeridian(new Location("Greenwich", 51.46, 0)));
        assertFalse(locationParser.isOnPrimeMeridian(new Location("Budapest", 47.497912, 19.040235)));
    }

    @Test
    void testLocationParser1() {
        System.out.println("test1: " + locationParser);
    }

    @Test
    void testLocationParser2() {
        System.out.println("test2: " + locationParser);
    }

    @Test
    void testDistanceFrom() {
        Location location1 = new Location("Budapest", 47.497912, 19.040235);
        Location location2 = new Location("Greenwich", 51.46, 0);
        assertEquals(1439.58, location1.distanceFrom(location2), 0.01);
    }

    @Test
    void testParseAtOnce() {
        Location location = new LocationParser().parse("Budapest,47.497912,19.040235");
        assertAll("Location",
                () -> assertEquals("Budapest", location.getName()),
                () -> assertEquals(47.497912, location.getLat(), 0.0005),
                () -> assertEquals(19.040235, location.getLon(), 0.0005));
    }
}