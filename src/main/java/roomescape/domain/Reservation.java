package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.exception.ReservationInvalidException;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    private Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(Long id, String name, LocalDate date, LocalTime time, Clock clock) {
        LocalDateTime reservationDateTime = LocalDateTime.of(date, time);
        if (reservationDateTime.isBefore(LocalDateTime.now(clock))) {
            throw new ReservationInvalidException("과거 시간으로 예약할 수 없습니다");
        }
        return new Reservation(id, name, date, time);
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
