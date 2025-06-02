package roomescape.domain;

import org.springframework.stereotype.Repository;
import roomescape.exception.ReservationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(0L);

    public Reservation save(Reservation reservation) {
        Reservation storedReservation = Reservation.of(
                sequence.incrementAndGet(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
        reservations.add(storedReservation);
        return storedReservation;
    }

    public void deleteById(Long id) {
        Reservation reservation = findById(id)
                .orElseThrow(() -> new ReservationException("[ERROR] 예약을 찾을 수 없습니다."));
        reservations.remove(reservation);
    }

    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst();
    }
}


