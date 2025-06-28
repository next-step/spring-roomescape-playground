package roomescape.reservation.domain;

import roomescape.reservation.dto.ReservationRequest;
import roomescape.time.domain.Time;

import java.time.LocalDate;

public record Reservation(Long id, String name, LocalDate date, Time time) {
    //memory 용
    public static Reservation of(ReservationRequest request, Long id) {
        return new Reservation (id, request.name(), request.date(), new Time(null, null));
    }

    //DB 용
    public static Reservation of(String name, LocalDate date, Time time) {
        return new Reservation(null, name, date, time);
    }
}
