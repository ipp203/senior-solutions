package meetingrooms;

public class MeetingRoom {
    private int id;
    private final String name;
    private final int length;
    private final int width;

    public MeetingRoom(String name, int length, int width) {
        this.name = name;
        this.length = length;
        this.width = width;
    }

    public MeetingRoom(int id, String name, int length, int width) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.width = width;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArea() {
        return width * length;
    }

    @Override
    public String toString() {
        return String.format("%s (%d x %d = %d)", name, length, width, getArea());
    }
}
