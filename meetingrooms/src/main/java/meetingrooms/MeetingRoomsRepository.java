package meetingrooms;

import java.util.List;

public interface MeetingRoomsRepository {

    void saveMeetingRoom(MeetingRoom meetingRoom);

    List<MeetingRoom> listRoomsOrderByName();

    List<MeetingRoom> listRoomsReverseOrderByName();

    List<MeetingRoom> listEverySecondRoom();

    List<MeetingRoom> listRoomOrderByArea();

    MeetingRoom findRoomByName(String name);

    List<MeetingRoom> findRoomByNameFragment(String fragment);

    List<MeetingRoom> findRoomLargerThan(int area);


}
