package roomescape.value;

import java.time.LocalDate;

public class Date {

    private LocalDate date;

    public Date(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
