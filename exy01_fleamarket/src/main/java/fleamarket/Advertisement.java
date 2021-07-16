package fleamarket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement implements Comparable<Advertisement> {
    private long id;
    private LumberCategory lumberCategory;
    private String text;
    private LocalDateTime timeStamp;

    public Advertisement(long id, CreateAdvertisementCommand command) {
        this.id = id;
        lumberCategory = command.getLumberCategory();
        text = command.getText();
        timeStamp = LocalDateTime.now();
    }

    public void setText(String text) {
        this.text = text;
        timeStamp = LocalDateTime.now();
    }

    @Override
    public int compareTo(Advertisement o) {
        return o.getTimeStamp().compareTo(timeStamp);
    }

}
