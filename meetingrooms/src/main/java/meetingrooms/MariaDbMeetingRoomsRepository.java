package meetingrooms;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MariaDbMeetingRoomsRepository implements MeetingRoomsRepository {

    private final JdbcTemplate jdbcTemplate;

    public MariaDbMeetingRoomsRepository() {
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

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
    }

    public MariaDbMeetingRoomsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveMeetingRoom(MeetingRoom meetingRoom) {
        jdbcTemplate.update("INSERT INTO meetingrooms(name,length,width) VALUES(?,?,?)",
                meetingRoom.getName(), meetingRoom.getLength(), meetingRoom.getWidth());
    }

    @Override
    public List<MeetingRoom> listRoomsOrderByName() {
        return jdbcTemplate.query("SELECT id, name, length, width FROM meetingrooms ORDER BY name",
                (rs, i) -> new MeetingRoom(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("length"),
                        rs.getInt("width")));
    }

    @Override
    public List<MeetingRoom> listRoomsReverseOrderByName() {
        return jdbcTemplate.query("SELECT id, name, length, width FROM meetingrooms ORDER BY name DESC",
                (rs, i) -> new MeetingRoom(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("length"),
                        rs.getInt("width")));
    }

    @Override
    public List<MeetingRoom> listEverySecondRoom() {

        List<MeetingRoom> result = new ArrayList<>();
        jdbcTemplate.query("SELECT id, name, length, width FROM meetingrooms ORDER BY name",
                (rs, i) -> {
                    MeetingRoom mr = new MeetingRoom(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("length"),
                            rs.getInt("width"));
                    if (i % 2 == 1) {
                        result.add(mr);
                    }
                    return null;
                });
        return result;

    }

    @Override
    public List<MeetingRoom> listRoomOrderByArea() {
        return jdbcTemplate.query("SELECT id, name, length, width FROM meetingrooms ORDER BY length*width DESC",
                (rs, i) -> new MeetingRoom(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("length"),
                        rs.getInt("width")));
    }

    @Override
    public MeetingRoom findRoomByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Argument is null");
        }
        try {
            return jdbcTemplate.queryForObject("SELECT id, name, length, width FROM meetingrooms WHERE name like ?",
                    (rs, i) -> new MeetingRoom(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("length"),
                            rs.getInt("width")),
                    name);
        } catch (EmptyResultDataAccessException erdae) {
            throw new IllegalArgumentException("Name not found.", erdae);
        }
    }

    @Override
    public List<MeetingRoom> findRoomByNameFragment(String fragment) {
        if (fragment == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        try {
            return jdbcTemplate.query("SELECT id, name, length, width FROM meetingrooms WHERE name like ?",
                    (rs, i) -> new MeetingRoom(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("length"),
                            rs.getInt("width")),
                    "%" + fragment + "%");
        } catch (EmptyResultDataAccessException erdae) {
            throw new IllegalArgumentException("Name not found.", erdae);
        }
    }

    @Override
    public List<MeetingRoom> findRoomLargerThan(int area) {
        return jdbcTemplate.query("SELECT id, name, length, width FROM meetingrooms WHERE length*width > ?",
                (rs, i) -> new MeetingRoom(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("length"),
                        rs.getInt("width")),
                area);
    }
}
