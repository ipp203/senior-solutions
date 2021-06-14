package location;

import org.junit.jupiter.api.*;

import java.util.List;

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

    @Test
    void testLocationCreateWithWrongCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> new Location("Dummy", -91.0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> new Location("Dummy", -1.0, 200.0));
    }

    class RepeatedTestClass {
        private final Location location;
        private final boolean bool;

        public RepeatedTestClass(Location location, boolean bool) {
            this.location = location;
            this.bool = bool;
        }

        public Location getLocation() {
            return location;
        }

        public boolean isBool() {
            return bool;
        }
    }

    private final List<RepeatedTestClass> locationsOnEquator = List.of(
            new RepeatedTestClass(new Location("Budapest", 47.497912, 19.040235), false),
            new RepeatedTestClass(new Location("Greenwich", 51.46, 0), false),
            new RepeatedTestClass(new Location("Quitu", 0, 78.5), true));

    @RepeatedTest(3)
    void testRepeatedIsOnEquator(RepetitionInfo repetitionInfo) {
        int i = repetitionInfo.getCurrentRepetition() - 1;
        assertEquals(locationsOnEquator.get(i).isBool(),
                locationParser.isOnEquator(locationsOnEquator.get(i).getLocation()));
    }

    private final List<RepeatedTestClass> locationsOnPrimeMeridian = List.of(
            new RepeatedTestClass(new Location("Budapest", 47.497912, 19.040235), false),
            new RepeatedTestClass(new Location("Greenwich", 51.46, 0), true),
            new RepeatedTestClass(new Location("Quitu", 0, 78.5), false));

    @RepeatedTest(value = 3, name = "testIsOnPrimeMeridian{currentRepetition}/{totalRepetitions}")
    void testRepeatedIsOnPrimeMeridian(RepetitionInfo repetitionInfo) {
        int i = repetitionInfo.getCurrentRepetition() - 1;
        assertEquals(locationsOnPrimeMeridian.get(i).isBool(),
                locationParser.isOnPrimeMeridian(locationsOnPrimeMeridian.get(i).getLocation()));
    }
}