package roomescape.domain.reservation.repository;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import roomescape.domain.reservation.domain.Reservation;

public class ReservationRepository extends ArrayList<Reservation> {

    public AtomicLong index = new AtomicLong(1);
}
