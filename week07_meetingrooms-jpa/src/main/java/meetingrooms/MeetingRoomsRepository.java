package meetingrooms;

import java.util.List;

public interface MeetingRoomsRepository {
    MeetingRoom save(String name, int width, int length);

    List<String> getMeetingroomsOrderedByName();

    List<String> getEverySecondMeetingRoom();

    List<MeetingRoom> getMeetingRooms();

    List<MeetingRoom> getExactMeetingRoomByName(String name);

    List<MeetingRoom> getMeetingRoomsByPrefix(String nameOrPrefix);

    void deleteAll();
}
