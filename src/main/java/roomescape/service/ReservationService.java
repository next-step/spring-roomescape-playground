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
        if (!StringUtils.hasText(request.name())) {
            throw new ReservationValidationException("이름은 공백일 수 없습니다.");
        }
        if (request.date() == null) {
            throw new ReservationValidationException("날짜는 공백일 수 없습니다.");
        }
        if (request.time() == null) {
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
                        new Reservation(id.incrementAndGet(), "브라운", LocalDate.parse("2025-01-01"), LocalTime.parse("10:00")),
                        new Reservation(id.incrementAndGet(), "브라운", LocalDate.parse("2025-01-02"), LocalTime.parse("11:00")),
                        new Reservation(id.incrementAndGet(), "브라운", LocalDate.parse("2025-01-03"), LocalTime.parse("12:00"))
                )
        );
    }
}
