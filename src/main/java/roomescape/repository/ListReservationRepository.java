package roomescape.repository;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

//@Repository
public class ListReservationRepository implements ReservationRepository {
    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    @Override
    public Reservation save(Reservation reservation) {
        Long newId = index.getAndIncrement();
        Reservation saved = new Reservation(
                newId,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
        reservations.add(saved);
        return saved;
    }

    @Override
    public boolean deleteById(Long id) {
        return reservations.removeIf(it -> Objects.equals(it.getId(), id));
    }

    @Override
    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return reservations.stream()
                .anyMatch(it -> it.getDate().equals(date) && it.getTime().equals(time));
    }
}