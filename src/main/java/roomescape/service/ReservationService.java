package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import roomescape.dto.ReservationCreateRequest;
import roomescape.exception.ReservationNotFoundException;
import roomescape.exception.ReservationValidationException;
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
        if (request.name() == null || request.name().isBlank()) {
            throw new ReservationValidationException("이름은 공백일 수 없습니다.");
        }
        if (request.date() == null || request.date().isBlank()) {
            throw new ReservationValidationException("날짜는 공백일 수 없습니다.");
        }
        if (request.time() == null || request.time().isBlank()) {
            throw new ReservationValidationException("시간은 공백일 수 없습니다.");
        }

        Reservation reservation = new Reservation(id.incrementAndGet(), request.name(), request.date(), request.time());
        reservations.add(reservation);

        return reservation;
    }

    public void deleteReservation(int id) {
       boolean removed = reservations.removeIf((reservation -> reservation.id() == id));

       if (!removed) {
           throw new ReservationNotFoundException("예약을 찾을 수 없습니다.");
       }
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
