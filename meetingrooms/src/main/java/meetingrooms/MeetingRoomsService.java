package meetingrooms;

import java.util.List;

public class MeetingRoomsService {
    //private final MeetingRoomsRepository roomsRepository = new InMemoryMeetingRoomsRepository();
    private final MeetingRoomsRepository roomsRepository = new MariaDbMeetingRoomsRepository();

    public void saveRoom(MeetingRoom meetingRoom) {
        roomsRepository.saveMeetingRoom(meetingRoom);
    }

    public List<MeetingRoom> listRoomsOrderByName() {
        return roomsRepository.listRoomsOrderByName();
    }

    public List<MeetingRoom> listRoomsReverseOrderByName() {
        return roomsRepository.listRoomsReverseOrderByName();
    }

    public List<MeetingRoom> listEverySecondRoom() {
        return roomsRepository.listEverySecondRoom();
    }

    public List<MeetingRoom> listRoomOrderByArea() {
        return roomsRepository.listRoomOrderByArea();
    }

    public MeetingRoom findRoomByName(String name) {
        return roomsRepository.findRoomByName(name);
    }

    public List<MeetingRoom> findRoomByNameFragment(String fragment) {
        return roomsRepository.findRoomByNameFragment(fragment);
    }

    public List<MeetingRoom> findRoomLargerThan(int area) {
        return roomsRepository.findRoomLargerThan(area);
    }
}
