package roomescape.repository;

import roomescape.entity.Reservation;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationRepository {

    private Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private AtomicLong index = new AtomicLong(0);

    public Reservation save(Reservation reservation) {

        Reservation createdReservation = Reservation.toEntityWithId(index.incrementAndGet(), reservation);
        this.reservations.put(createdReservation.getId(), createdReservation);

        return createdReservation;
    }

    public List<Reservation> findAll() {
        return this.reservations.values().stream().toList();
    }
}
