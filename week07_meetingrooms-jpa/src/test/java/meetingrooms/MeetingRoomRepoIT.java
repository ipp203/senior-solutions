package meetingrooms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MeetingRoomRepoIT {

    MeetingRoomRepo repo;

    @BeforeEach
    void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceunit");
        repo = new MeetingRoomRepo(factory);
    }


    @Test
    void getMeetingroomsOrderedByName() {
        repo.save("BBBB", 12, 12);
        repo.save("AAAA", 10, 10);
        repo.save("CC", 5, 2);

        List<String> names = repo.getMeetingroomsOrderedByName();

        assertEquals(3, names.size());
        assertEquals("AAAA", names.get(0));
    }

    @Test
    void getEverySecondMeetingRoom() {
        repo.save("BBBB", 12, 12);
        repo.save("AAAA", 10, 10);
        repo.save("DD", 5, 2);
        repo.save("CC", 15, 12);

        List<String> names = repo.getEverySecondMeetingRoom();

        assertEquals(2, names.size());
        assertEquals("BBBB", names.get(0));
        assertEquals("DD", names.get(1));

    }

    @Test
    void getMeetingRooms() {
        repo.save("BBBB", 12, 12);
        repo.save("AAAA", 10, 10);
        repo.save("DD", 5, 2);
        repo.save("CC", 15, 12);

        List<MeetingRoom> rooms = repo.getMeetingRooms();

        assertEquals(4, rooms.size());
    }

    @Test
    void getExactMeetingRoomByName() {
        repo.save("BBBB", 12, 12);
        repo.save("AAAA", 10, 10);
        repo.save("DD", 5, 2);
        repo.save("CC", 15, 12);

        List<MeetingRoom> rooms = repo.getExactMeetingRoomByName("BBBB");

        assertEquals(1, rooms.size());
        assertEquals("BBBB", rooms.get(0).getName());
    }

    @Test
    void getMeetingRoomsByPrefix() {
        repo.save("AABB", 12, 12);
        repo.save("AAAA", 10, 10);
        repo.save("DD", 5, 2);
        repo.save("CC", 15, 12);

        List<MeetingRoom> rooms = repo.getMeetingRoomsByPrefix("AA");

        assertEquals(2, rooms.size());
        assertEquals("AABB", rooms.get(1).getName());
    }

    @Test
    void deleteAll() {
        repo.save("BBBB", 12, 12);
        repo.save("AAAA", 10, 10);
        repo.save("DD", 5, 2);
        repo.save("CC", 15, 12);

        List<MeetingRoom> rooms = repo.getMeetingRooms();
        assertEquals(4, rooms.size());

        repo.deleteAll();

        rooms = repo.getMeetingRooms();
        assertEquals(0, rooms.size());


    }
}