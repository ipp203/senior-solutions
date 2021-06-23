package usedcars.model;

import java.time.LocalDate;

public class KmState {
    private final LocalDate date;
    private final int km;

    public KmState(LocalDate date, int km) {
        this.date = date;
        this.km = km;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getKm() {
        return km;
    }
}
