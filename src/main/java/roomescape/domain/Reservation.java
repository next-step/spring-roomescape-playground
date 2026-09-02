package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import roomescape.exception.InvalidReservationRequestException;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        validate(name, date, time);
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
        Reservation reservation = new Reservation(null, name, date, time);
        reservation.validateNotPast(now);
        return reservation;
    }

    public Reservation withId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("예약 ID는 null일 수 없습니다.");
        }
        return new Reservation(id, name, date, time);
    }

    private void validate(String name, LocalDate date, ReservationTime time) {
        if (name == null || name.isBlank() || date == null || time == null) {
            throw new InvalidReservationRequestException();
        }
    }

    private void validateNotPast(LocalDateTime now) {
        LocalDateTime reservationDateTime = LocalDateTime.of(date, time.getTime());
        if (reservationDateTime.isBefore(now)) {
            throw new InvalidReservationRequestException("지난 일시로는 예약할 수 없습니다.");
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
