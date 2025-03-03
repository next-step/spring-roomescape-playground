package roomescape.domain.reservation.domain;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.domain.reservationTime.domain.ReservationTime;
import roomescape.global.exception.RoomescapeBadRequestException;

public class Reservation {

    private final Long id;

    private final String name;

    private final LocalDate date;

    private final ReservationTime reservationTime;

    public Reservation(final String name, final LocalDate date, final ReservationTime reservationTime) {
        this(null, name, date, reservationTime);
    }

    public Reservation(final Long id, final String name, final LocalDate date, final ReservationTime reservationTime) {
        if (name == null || name.isEmpty() || name.getBytes(StandardCharsets.UTF_8).length > 255
                || reservationTime == null) {
            throw new RoomescapeBadRequestException("예약 이름과 예약 시간은 필수입니다.");
        }
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
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

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public LocalTime getTime() {
        return reservationTime.getTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(date, that.date) && Objects.equals(reservationTime,
                that.reservationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, reservationTime);
    }
}
