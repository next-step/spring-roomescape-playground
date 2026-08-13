package roomescape.domain;

import roomescape.exception.NotFoundReservationException;

import java.util.ArrayList;
import java.util.List;

public class Reservations {
    private final List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> getReservations() {
        return List.copyOf(reservations);
    }

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    public void delete(long id) {
        boolean exists = reservations.stream().anyMatch(reservation -> reservation.getId() == id);

        if (!exists) {
            throw new NotFoundReservationException("해당 id의 예약을 찾을 수 없습니다.");
        }

        reservations.removeIf(reservation -> reservation.getId() == id);
    }
}
