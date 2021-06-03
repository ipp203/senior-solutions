package meetingrooms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryMeetingRoomsRepositoryTest {
    InMemoryMeetingRoomsRepository immrrEmpty;
    InMemoryMeetingRoomsRepository immrrReady;
    List<MeetingRoom> rooms;

    @BeforeEach
    void init(){
        rooms = Arrays.asList(
                new MeetingRoom(1,"aaa",7,8),
                new MeetingRoom(2,"aáa",3,4),
                new MeetingRoom(3,"AAA",5,6),
                new MeetingRoom(4,"AÁA",1,2));
        immrrEmpty = new InMemoryMeetingRoomsRepository();
        immrrReady = new InMemoryMeetingRoomsRepository(rooms);
    }

    @Test
    void saveMeetingRoom() {
        for (MeetingRoom room:rooms) {
          immrrEmpty.saveMeetingRoom(room);
        }
        immrrEmpty.saveMeetingRoom(null);

        List<MeetingRoom> result = immrrEmpty.getMeetingRoomList();
        assertEquals(4,result.size());
    }

    @Test
    void listRoomsOrderByName() {
        List<MeetingRoom> result = immrrReady.listRoomsOrderByName();
        assertEquals(4,result.size());
        assertEquals("AAA",result.get(1).getName());
        assertEquals("aáa",result.get(2).getName());
    }

    @Test
    void listRoomsReverseOrderByName() {
        List<MeetingRoom> result = immrrReady.listRoomsReverseOrderByName();
        assertEquals(4,result.size());
        assertEquals("AAA",result.get(2).getName());
        assertEquals("aáa",result.get(1).getName());
    }

    @Test
    void listEverySecondRoom() {
        List<MeetingRoom> result = immrrReady.listEverySecondRoom();
        assertEquals(2,result.size());
        assertEquals("AAA",result.get(0).getName());
        assertEquals("AÁA",result.get(1).getName());
    }

    @Test
    void listRoomOrderByArea() {
        List<MeetingRoom> result = immrrReady.listRoomOrderByArea();
        assertEquals(4,result.size());
        assertEquals("aaa",result.get(0).getName());
        assertEquals("aáa (3 x 4 = 12)",result.get(2).toString());
    }

    @Test
    void findRoomByName() {
        MeetingRoom room = immrrReady.findRoomByName("aaa");
        assertEquals("aaa",room.getName());
    }

    @Test
    void findRoomByNameGoesToException() {
        assertThrows(IllegalArgumentException.class,()-> immrrReady.findRoomByName(null));
        assertThrows(IllegalArgumentException.class,()-> immrrReady.findRoomByName(""));
        assertThrows(IllegalArgumentException.class,()-> immrrReady.findRoomByName("ccc"));
    }


    @Test
    void findRoomByNameFragment() {
        assertEquals(2,immrrReady.findRoomByNameFragment("aá").size());
        assertEquals(0,immrrReady.findRoomByNameFragment("c").size());
    }
    @Test
    void findRoomByNameFragmentWithNull() {
        assertThrows(IllegalArgumentException.class,()->immrrReady.findRoomByNameFragment(null));
    }

    @Test
    void findRoomLargerThan() {
        assertEquals(4,immrrReady.findRoomLargerThan(0).size());
        List<MeetingRoom> rooms = immrrReady.findRoomLargerThan(3*4);
        assertEquals(2,rooms.size());
    }
}