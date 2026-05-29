package roomescape;

import java.time.LocalDate;
import roomescape.exception.InvalidReservationException;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private Time time;

    private Reservation(Long id, String name, LocalDate date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation createNew(String name, LocalDate date, Time time) {
        validateDate(date);
        return new Reservation(null, name, date, time);
    }

    public static Reservation fromEntity(Long id, String name, LocalDate date, Time time) {
        return new Reservation(id, name, date, time);
    }

    private static void validateDate(LocalDate date) {
        if (date != null && date.isBefore(LocalDate.now())) {
            throw new InvalidReservationException("과거 날짜로는 예약할 수 없습니다.");
        }
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public Time getTime() { return time; }
}
