package roomescape.dto;

import java.util.Collection;
import java.util.List;
import roomescape.domain.Reservation;

public class ReservationReadResponse {
    private final List<Reservation> reservations;
    private ReservationReadResponse(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public static ReservationReadResponse toDto(List<Reservation> reservations) {
        return new ReservationReadResponse(reservations);
    }

    public List<Reservation> getLists() {
        return this.reservations;
    }
}
