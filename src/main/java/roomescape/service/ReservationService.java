package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.RequestReservation;
import roomescape.domain.Reservation;
import roomescape.global.exception.NotFoundException;

@Service
public class ReservationService {

    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    public Reservation create(final RequestReservation requestReservation) {
        Long id = index.getAndIncrement();
        requestReservation.validatePasted();
        Reservation reservation = Reservation.of(id, requestReservation.name(),
                requestReservation.parseDate(), requestReservation.parseTime());
        reservations.add(reservation);
        return reservation;
    }

    public List<Reservation> findAll() {
        return reservations;
    }

    public Reservation findById(final Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("예약 ID가 존재하지 않아요."));
    }

    public void delete(final Long id) {
        Reservation reservation = findById(id);
        reservations.remove(reservation);
    }
}
