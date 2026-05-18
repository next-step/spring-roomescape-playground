package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import roomescape.exception.ReservationConflictException;

public class Reservations {
    private final List<Reservation> reservations;

    public Reservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void validateDuplicate(Reservation target) {
        boolean isDuplicate = reservations.stream()
                .anyMatch(reservation -> reservation.isSameTime(target.getDate(), target.getTime()));

        if (isDuplicate) {
            throw new ReservationConflictException("중복된 예약이 존재합니다.");
        }
    }
}
