package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import roomescape.dto.ReservationCreateRequest;
import roomescape.model.Reservation;

public class ReservationService {
    private final AtomicInteger id;
    private final List<Reservation> reservations;

    public ReservationService() {
        this.id = new AtomicInteger(0);
        this.reservations = new ArrayList<>();

//        populateDefaults();
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public Reservation createReservation(ReservationCreateRequest request) {
        Reservation reservation = new Reservation(id.incrementAndGet(), request.name(), request.date(), request.time());
        reservations.add(reservation);

        return reservation;
    }

    public void deleteReservation(int id) {
       reservations.removeIf((reservation -> reservation.id() == id));
    }

    private void populateDefaults() {
        reservations.addAll(
                List.of(
                        new Reservation(id.incrementAndGet(), "브라운", "2025-01-01", "10:00"),
                        new Reservation(id.incrementAndGet(), "브라운", "2025-01-02", "11:00"),
                        new Reservation(id.incrementAndGet(), "브라운", "2025-01-03", "12:00")
                )
        );
    }
}
