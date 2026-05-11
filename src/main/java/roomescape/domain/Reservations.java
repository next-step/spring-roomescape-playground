package roomescape.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exceptions.AlreadyReservedException;
import roomescape.exceptions.ReservationNotFoundException;

public class Reservations {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public ReservationResponse add(ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toReservation(nextId());
        checkConflict(reservation);
        reservations.add(reservation);
        return ReservationResponse.fromReservation(reservation);
    }

    public void delete(Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(ReservationNotFoundException::new);
        reservations.remove(reservation);
    }

    private void checkConflict(Reservation newReservation) {
        if (reservations.stream().anyMatch(reservation -> reservation.conflicts(newReservation))) {
            idCounter.decrementAndGet();
            throw new AlreadyReservedException();
        }
    }

    private Long nextId() {
        return idCounter.getAndIncrement();
    }

    public List<Reservation> get() {
        return reservations;
    }
}
