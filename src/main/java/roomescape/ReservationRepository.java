package roomescape;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst();
    }

    public Reservation save(ReservationRequest request) {
        Reservation reservation = new Reservation(
                index.getAndIncrement(),
                request.getName(),
                request.getDate(),
                request.getTime()
        );

        reservations.add(reservation);
        return reservation;
    }

    public Reservation update(Long id, ReservationRequest request) {
        Reservation reservation = findById(id)
                .orElseThrow(NotFoundReservationException::new);

        reservation.update(
                request.getName(),
                request.getDate(),
                request.getTime()
        );

        return reservation;
    }

    public void delete(Long id) {
        Reservation reservation = findById(id)
                .orElseThrow(NotFoundReservationException::new);

        reservations.remove(reservation);
    }
}
