package roomescape.service;

import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    public static void  setReservations(List<Reservation> reservations) {
        reservations.add(new Reservation( "브라운", "2025-01-01", "10:00"));
        reservations.add(new Reservation("코니", "2025-01-02", "11:00"));
    }
}
