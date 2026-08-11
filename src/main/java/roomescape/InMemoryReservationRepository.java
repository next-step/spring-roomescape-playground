package roomescape;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(3);

    public InMemoryReservationRepository() {
        initializeReservations();
    }

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
    public void delete(Reservation reservation) {
        reservations.removeIf(savedReservation -> reservation.getId().equals(savedReservation.getId()));
    }

    @Override
    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.getDate().equals(date)
                        && reservation.getTime().equals(time));
    }

    private void initializeReservations() {
        reservations.add(new Reservation(1L, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)));
        reservations.add(new Reservation(2L, "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0)));
        reservations.add(new Reservation(3L, "브라운", LocalDate.of(2023, 1, 3), LocalTime.of(12, 0)));
    }
}
