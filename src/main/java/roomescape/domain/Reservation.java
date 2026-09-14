package roomescape.domain;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import roomescape.exception.ReservationInvalidException;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    private Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        if (name == null || name.isBlank()) {
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

    public static Reservation createNewReservation(String name, LocalDate date, ReservationTime time, Clock clock) {
        Reservation reservation = new Reservation(null, name, date, time);
        LocalDateTime reservationDateTime = LocalDateTime.of(date, time.getTime());
        if (reservationDateTime.isBefore(LocalDateTime.now(clock))) {
            throw new ReservationInvalidException("과거 시간으로 예약할 수 없습니다");
        }
        return reservation;
    }

    public static Reservation createFromPersistedData(Long id, String name, LocalDate date, ReservationTime time) {
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

    public ReservationTime getTime() {
        return time;
    }
}
