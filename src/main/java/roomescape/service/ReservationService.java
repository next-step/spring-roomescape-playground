package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import roomescape.dto.ReservationCreateRequest;
import roomescape.exception.ReservationNotFoundException;
import roomescape.exception.ReservationValidationException;
import roomescape.model.Reservation;

@Service
public class ReservationService {
    private final AtomicInteger id;
    private final List<Reservation> reservations;

    public ReservationService() {
        this.id = new AtomicInteger(0);
        this.reservations = Collections.synchronizedList(new ArrayList<>());

//        populateDefaults();
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public Reservation createReservation(ReservationCreateRequest request) {
        LocalDate date = LocalDate.parse(request.date());
        LocalTime time = LocalTime.parse(request.time());

        Reservation reservation = new Reservation(id.incrementAndGet(), request.name(), date, time);
        reservations.add(reservation);

        return reservation;
    }

    public void deleteReservation(int id) {
       reservations.removeIf((reservation -> reservation.id() == id));
    }

    private void populateDefaults() {
        reservations.addAll(
                List.of(
                        new Reservation(id.incrementAndGet(), "브라운", LocalDate.parse("2025-01-01"), LocalTime.parse("10:00")),
                        new Reservation(id.incrementAndGet(), "브라운", LocalDate.parse("2025-01-02"), LocalTime.parse("11:00")),
                        new Reservation(id.incrementAndGet(), "브라운", LocalDate.parse("2025-01-03"), LocalTime.parse("12:00"))
                )
        );
    }
}
