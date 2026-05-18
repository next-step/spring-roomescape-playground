package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.exception.InvalidReservationException;

public class ReservationRequest {

    private String name;
    private LocalDate date;
    private LocalTime time;


    public ReservationRequest(String name, LocalDate date, LocalTime time) {
        validate(name, date, time);
        this.name = name;
        this.date = date;
        this.time = time;
    }


    private static void validate(String name, LocalDate date, LocalTime time) {
        if (name == null || name.isBlank() || date == null || time == null) {
            throw new InvalidReservationException("필수 값이 누락되었습니다.");
        }
    }


    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
}
