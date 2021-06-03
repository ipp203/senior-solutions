package meetingrooms;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MariaDbMeetingRoomsRepositoryTest {
    private MariaDbMeetingRoomsRepository mmrr;
    private List<MeetingRoom> rooms;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        rooms = Arrays.asList(
                new MeetingRoom(1, "aaa", 7, 8),
                new MeetingRoom(2, "bbb", 3, 4),
                new MeetingRoom(3, "ccc", 5, 6),
                new MeetingRoom(4, "ddd", 1, 2));

        MariaDbDataSource dataSource;
        try {
            dataSource = new MariaDbDataSource();
            dataSource.setUrl("jdbc:mariadb://localhost:3306/employees?useUnicode=true");
            dataSource.setUser("employees");
            dataSource.setPassword("employees");
        } catch (SQLException e) {
            throw new IllegalStateException("Can not connect", e);
        }
        jdbcTemplate = new JdbcTemplate(dataSource);
        mmrr = new MariaDbMeetingRoomsRepository(jdbcTemplate);

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        for (MeetingRoom room : rooms) {
            mmrr.saveMeetingRoom(room);
        }
    }

    @Test
    void saveMeetingRoom() {

        Integer numberOfRows = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM meetingrooms",(rs,i)->rs.getInt(1));
        assertEquals(4,numberOfRows);
    }

    @Test
    void listRoomsOrderByName() {
        List<MeetingRoom> result = mmrr.listRoomsOrderByName();
        assertEquals(4,result.size());
        assertEquals("bbb",result.get(1).getName());
        assertEquals("ccc",result.get(2).getName());
    }

    @Test
    void listRoomsReverseOrderByName() {
        List<MeetingRoom> result = mmrr.listRoomsReverseOrderByName();
        assertEquals(4,result.size());
        assertEquals("bbb",result.get(2).getName());
        assertEquals("ccc",result.get(1).getName());
    }

    @Test
    void listEverySecondRoom() {
        List<MeetingRoom> result = mmrr.listEverySecondRoom();
        assertEquals(2,result.size());
        assertEquals("bbb",result.get(0).getName());
        assertEquals("ddd",result.get(1).getName());
    }

    @Test
    void listRoomOrderByArea() {
        List<MeetingRoom> result = mmrr.listRoomOrderByArea();
        assertEquals(4,result.size());
        assertEquals("aaa",result.get(0).getName());
        assertEquals("bbb (3 x 4 = 12)",result.get(2).toString());
    }

    @Test
    void findRoomByName() {
        MeetingRoom room = mmrr.findRoomByName("aaa");
        assertEquals("aaa",room.getName());
    }

    @Test
    void findRoomByNameGoesToException() {
        assertThrows(IllegalArgumentException.class,()-> mmrr.findRoomByName(null));
        assertThrows(IllegalArgumentException.class,()-> mmrr.findRoomByName(""));
        assertThrows(IllegalArgumentException.class,()-> mmrr.findRoomByName("kkk"));
    }


    @Test
    void findRoomByNameFragment() {
        assertEquals(1,mmrr.findRoomByNameFragment("bb").size());
        assertEquals(0,mmrr.findRoomByNameFragment("k").size());
    }
    @Test
    void findRoomByNameFragmentWithNull() {
        assertThrows(IllegalArgumentException.class,()->mmrr.findRoomByNameFragment(null));
    }

    @Test
    void findRoomLargerThan() {
        assertEquals(4,mmrr.findRoomLargerThan(0).size());
        List<MeetingRoom> rooms = mmrr.findRoomLargerThan(3*4);
        assertEquals(2,rooms.size());
    }
}