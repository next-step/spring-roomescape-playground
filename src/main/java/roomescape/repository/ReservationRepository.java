package roomescape.repository;

import roomescape.entity.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationRepository {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    public Reservation save(Reservation reservation) {

        Reservation createdReservation = Reservation.toEntity(index.incrementAndGet(), reservation);
        this.reservations.add(createdReservation);

        return createdReservation;
    }

    public List<Reservation> findAll() {
        return this.reservations;
    }
}
