package meetingrooms;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InMemoryMeetingRoomsRepository implements MeetingRoomsRepository {
    private final List<MeetingRoom> meetingRoomList;
    private final Locale localeHU = new Locale("HU", "hu");
    private final Collator collatorHU = Collator.getInstance(localeHU);

    public InMemoryMeetingRoomsRepository() {
        meetingRoomList = new ArrayList<>();
    }

    public InMemoryMeetingRoomsRepository(List<MeetingRoom> meetingRoomList) {
        this.meetingRoomList = new ArrayList<>(meetingRoomList);
    }

    public List<MeetingRoom> getMeetingRoomList() {
        return new ArrayList<>(meetingRoomList);
    }

    @Override
    public void saveMeetingRoom(MeetingRoom meetingRoom) {
        if (meetingRoom != null) {
            if (meetingRoom.getId() == 0) {
                meetingRoom.setId(meetingRoomList.size() + 1);
            }
            meetingRoomList.add(meetingRoom);
        }
    }

    @Override
    public List<MeetingRoom> listRoomsOrderByName() {
        return meetingRoomList.stream()
                .sorted(Comparator.comparing(MeetingRoom::getName, collatorHU))
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingRoom> listRoomsReverseOrderByName() {
        return meetingRoomList.stream()
                .sorted(Comparator.comparing(MeetingRoom::getName, collatorHU).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingRoom> listEverySecondRoom() {
        List<MeetingRoom>orderedList = meetingRoomList.stream()
                .sorted(Comparator.comparing(MeetingRoom::getName, collatorHU))
                .collect(Collectors.toList());

        return IntStream.range(0,meetingRoomList.size())
                .filter(i->i%2==1)
                .mapToObj(orderedList::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingRoom> listRoomOrderByArea() {
        return meetingRoomList.stream()
                .sorted(Comparator.comparing(MeetingRoom::getArea).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public MeetingRoom findRoomByName(String name) {
        return meetingRoomList.stream()
                .filter(mr -> mr.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Name not found."));
    }

    @Override
    public List<MeetingRoom> findRoomByNameFragment(String fragment) {
        if (fragment == null){
            throw new IllegalArgumentException("Parameter is null");
        }
        return meetingRoomList.stream()
                .filter(mr -> mr.getName().toLowerCase(localeHU).contains(fragment.toLowerCase(localeHU)))
                .sorted(Comparator.comparing(MeetingRoom::getName, collatorHU))
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingRoom> findRoomLargerThan(int area) {
        return meetingRoomList.stream()
                .filter(mr -> mr.getArea() > area)
                .sorted(Comparator.comparing(MeetingRoom::getArea))
                .collect(Collectors.toList());
    }
}
