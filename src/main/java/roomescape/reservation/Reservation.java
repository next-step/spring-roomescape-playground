package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(Long id, String name, LocalDate date, LocalTime time) {
    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation (id, reservation.name, reservation.date, reservation.time);
    }
}
