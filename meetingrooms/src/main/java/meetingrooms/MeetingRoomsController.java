package meetingrooms;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class MeetingRoomsController {
    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    private final MeetingRoomsService meetingRoomsService = new MeetingRoomsService();

    private void printMenu() {

        System.out.println("-------------------------------------------------------\n" +
                "0. Tárgyaló rögzítése\n" +
                "1. Tárgyalók névsorrendben     " +
                "2. Tárgyalók név alapján visszafele sorrendben\n" +
                "3. Minden második tárgyaló     " +
                "4. Terület alapú lista\n" +
                "5. Keresés pontos név alapján  " +
                "6. Keresés névtöredék alapján\n" +
                "7. Keresés terület alapján\n" +
                "8. Kilépés\n" +
                "---------------------------------------------------------");
    }

    private <T> T getInput(Function<String, T> parse, Predicate<T> pre, String inputMessage, String exceptionMessage) {
        T result = null;
        do {
            String input = getInputData(inputMessage);
            try {
                result = parse.apply(input);
            } catch (NumberFormatException nfe) {
                System.out.println(exceptionMessage);
            }
        } while (pre.test(result));
        return result;
    }


    private int getMenuNumber() {
        printMenu();
        return getInput(Integer::parseInt, i -> i == null || i < 0 || i > 8, "menüpontot", "Számot adjon meg!");
    }

    private String getInputData(String str) {
        System.out.println("Adja meg a " + str + "!");
        return scanner.nextLine();
    }

    private void setNewMeetingRoom() {
        System.out.println("Tárgyaló rögzítése: ");
        String name = getInput(Function.identity(), Objects::isNull, "tárgyaló nevét", "");
        int length = getInput(Integer::parseInt, Objects::isNull, "tárgyaló hosszát", "Számot adjon meg!");
        int width = getInput(Integer::parseInt, Objects::isNull, "tárgyaló szélességét", "Számot adjon meg!");
        meetingRoomsService.saveRoom(new MeetingRoom(name, length, width));
    }

    private void printList(List<MeetingRoom> rooms, Function<MeetingRoom, String> getString) {
        for (MeetingRoom room : rooms) {
            System.out.println(getString.apply(room));
        }
    }

    private void printLargestRooms() {
        int area = getInput(Integer::parseInt, i -> i == null || i < 1, "területet", "Számot adjon meg!");
        printList(meetingRoomsService.findRoomLargerThan(area), MeetingRoom::toString);
    }

    private void printRoomByName() {
        String name = getInput(Function.identity(), Objects::isNull, "tárgyaló nevét", "");
        try {
            MeetingRoom result = meetingRoomsService.findRoomByName(name);
            System.out.println(result.toString());
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        }
    }

    private void printRoomByNameFragment() {
        String fragment = getInput(Function.identity(), Objects::isNull, "tárgyaló nevének részletét", "");
        printList(meetingRoomsService.findRoomByNameFragment(fragment), MeetingRoom::toString);
    }

    public static void main(String[] args) {
        System.out.println("Tárgyaló nyilvántartó program");
        MeetingRoomsController mrc = new MeetingRoomsController();
        int number;
        do {
            number = mrc.getMenuNumber();
            switch (number) {
                case 0: {
                    mrc.setNewMeetingRoom();
                    break;
                }
                case 1: {
                    mrc.printList(mrc.meetingRoomsService.listRoomsOrderByName(), MeetingRoom::getName);
                    break;
                }
                case 2: {
                    mrc.printList(mrc.meetingRoomsService.listRoomsReverseOrderByName(), MeetingRoom::getName);
                    break;
                }
                case 3: {
                    mrc.printList(mrc.meetingRoomsService.listEverySecondRoom(), MeetingRoom::getName);
                    break;
                }
                case 4: {
                    mrc.printList(mrc.meetingRoomsService.listRoomOrderByArea(), MeetingRoom::toString);
                    break;
                }
                case 5: {
                    mrc.printRoomByName();
                    break;
                }
                case 6: {
                    mrc.printRoomByNameFragment();
                    break;
                }
                case 7: {
                    mrc.printLargestRooms();
                    break;
                }
            }
        }
        while (number != 8);
    }


}
