package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;

@Repository
public class MemoryReservationRepository implements ReservationRepository {

    private static final AtomicLong index = new AtomicLong(0);
    private static final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    @Override
    public Reservation save(Reservation request) {
        Reservation reservation = new Reservation(
            index.incrementAndGet(),
            request.getName(),
            request.getDate(),
            request.getTime()
        );
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public void deleteById(Long id) {
        Reservation reservation = reservations.stream()
            .filter(it -> it.getId().equals(id))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
        reservations.remove(reservation);
    }
}
