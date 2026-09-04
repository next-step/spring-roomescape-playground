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
        if (name == null || name.isBlank()){
            throw new ReservationInvalidException("예약자 이름은 비워둘 수 없습니다.");
        }

        if (date == null) {
            throw new ReservationInvalidException("예약 날짜는 비어 있을 수 없습니다");
        }

        if (time == null) {
            throw new ReservationInvalidException("예약 시간은 비어 있을 수 없습니다");
        }

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation createNewReservation(String name, LocalDate date, LocalTime time, Clock clock) {
        LocalDateTime reservationDateTime = LocalDateTime.of(date, time);
        if (reservationDateTime.isBefore(LocalDateTime.now(clock))) {
            throw new ReservationInvalidException("과거 시간으로 예약할 수 없습니다");
        }
        return new Reservation(null, name, date, time);
    }

    public static Reservation createFromPersistedData(Long id, String name, LocalDate date, LocalTime time) {
        if (id == null) {
            throw new ReservationInvalidException("DB에 저장된 예약의 ID는 비어있을 수 없습니다.");
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
