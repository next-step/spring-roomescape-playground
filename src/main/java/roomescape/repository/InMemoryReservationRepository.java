package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {
    private final static AtomicLong id = new AtomicLong(0);
    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    @Override
    public Reservation save(String name, LocalDate date, LocalTime time) {
        Reservation reservation = new Reservation(id.incrementAndGet(), name, date, time);
        reservations.add(reservation);

        return reservation;
    }

    @Override
    public boolean deleteById(long id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId() == id);

        return removed;
    }
}
