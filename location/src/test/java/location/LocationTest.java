package location;


import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

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

    @Test
    void testLocationCreateWithWrongCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> new Location("Dummy", -91.0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> new Location("Dummy", -1.0, 200.0));
    }


    private static Stream<Arguments> createLocations() {
        return Stream.of(
                arguments(new Location("Budapest", 47.497912, 19.040235), false),
                arguments(new Location("Greenwich", 51.46, 0), false),
                arguments(new Location("Quitu", 0, 78.5), true));
    }

    @ParameterizedTest
    @MethodSource("createLocations")
    void testRepeatedIsOnEquator(Location location,boolean result) {
        assertEquals(result,
                locationParser.isOnEquator(location));
    }

    private Object[][] locationsOnPrimeMeridian = new Object[][]{
            {new Location("Budapest", 47.497912, 19.040235), false},
            {new Location("Greenwich", 51.46, 0), true},
            {new Location("Quitu", 0, 78.5), false}};

    @RepeatedTest(value = 3, name = "testIsOnPrimeMeridian{currentRepetition}/{totalRepetitions}")
    void testRepeatedIsOnPrimeMeridian(RepetitionInfo repetitionInfo) {
        int i = repetitionInfo.getCurrentRepetition() - 1;
        assertEquals(locationsOnPrimeMeridian[i][1],
                locationParser.isOnPrimeMeridian((Location)locationsOnPrimeMeridian[i][0]));
    }
}