package roomescape.domain;

import java.util.List;
import roomescape.exception.ReservationConflictException;

public class Reservations {
    private final List<Reservation> reservations;

    public Reservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void validateDuplicate(Reservation target) {
        boolean isDuplicate = reservations.stream()
                .anyMatch(reservation -> reservation.isSameTime(target.getDate(), target.getTimeInfo()));

        if (isDuplicate) {
            throw new ReservationConflictException("중복된 예약이 존재합니다.");
        }
    }

    public List<Reservation> getReservations(){
        return reservations;
    }
}
