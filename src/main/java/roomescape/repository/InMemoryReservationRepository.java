package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    @Override
    public Reservation save(Reservation reservation) {
        Reservation savedReservation = new Reservation(
                index.incrementAndGet(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );

        reservations.add(savedReservation);
        return savedReservation;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservation -> id.equals(reservation.getId()))
                .findFirst();
    }

    @Override
    public boolean deleteById(Long id) {
        return reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

    @Override
    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.getDate().equals(date)
                        && reservation.getTime().equals(time));
    }
}
