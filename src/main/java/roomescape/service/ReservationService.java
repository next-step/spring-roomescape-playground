package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.RequestReservation;
import roomescape.domain.Reservation;

@Service
public class ReservationService {

    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    public Reservation create(final RequestReservation requestReservation) {
        Long id = index.getAndIncrement();
        Reservation reservation = Reservation.of(id, requestReservation);
        reservations.add(reservation);
        return reservation;
    }

    public List<Reservation> findAll() {
        return reservations;
    }
}
