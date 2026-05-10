package roomescape.domain;

import roomescape.exception.InvalidReservationTimeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {
    private final String name;
    private final LocalDate date;
    private final LocalTime time;
    private Long id;

    public Reservation(String name, LocalDate date, LocalTime time) {
        validateReservationDateTime(date, time);
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private void validateReservationDateTime(LocalDate date, LocalTime time) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservationDateTime = date.atTime(time);

        if (reservationDateTime.isBefore(now)) {
            throw new InvalidReservationTimeException("과거 시간은 예약할 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
