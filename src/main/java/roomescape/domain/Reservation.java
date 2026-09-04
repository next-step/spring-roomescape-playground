package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import roomescape.exception.InvalidReservationException;

public final class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    private Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(
            String name,
            LocalDate date,
            ReservationTime time,
            LocalDateTime now
    ) {
        Objects.requireNonNull(now, "현재 일시는 null일 수 없습니다.");
        validate(name, date, time);
        validateNotPast(date, time, now);
        return new Reservation(null, name, date, time);
    }

    public static Reservation restore(Long id, String name, LocalDate date, ReservationTime time) {
        if (id == null) {
            throw new IllegalArgumentException("예약 ID는 null일 수 없습니다.");
        }
        validate(name, date, time);
        return new Reservation(id, name, date, time);
    }

    private static void validate(String name, LocalDate date, ReservationTime time) {
        if (name == null || name.isBlank()) {
            throw new InvalidReservationException("예약자 이름은 필수입니다.");
        }
        if (date == null) {
            throw new InvalidReservationException("예약 날짜는 필수입니다.");
        }
        if (time == null) {
            throw new InvalidReservationException("예약 시간은 필수입니다.");
        }
    }

    private static void validateNotPast(LocalDate date, ReservationTime time, LocalDateTime now) {
        LocalDateTime reservationDateTime = LocalDateTime.of(date, time.getTime());
        if (reservationDateTime.isBefore(now)) {
            throw new InvalidReservationException("지난 일시로는 예약할 수 없습니다.");
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

    public ReservationTime getTime() {
        return time;
    }
}
