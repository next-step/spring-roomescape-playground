package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.exception.InvalidReservationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private final AtomicLong index = new AtomicLong(0);
    private final List<Reservation> reservations = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    public ReservationController() {
        reservations.add(new Reservation(index.incrementAndGet(), "브라운", "2023-01-01", "10:00"));
        reservations.add(new Reservation(index.incrementAndGet(), "브라운", "2023-01-02", "11:00"));
        reservations.add(new Reservation(index.incrementAndGet(), "브라운", "2023-01-03", "12:00"));
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest request) {
        validateReservationRequest(request);

        Reservation reservation = new Reservation(
                index.incrementAndGet(),
                request.getName(),
                request.getDate(),
                request.getTime()
        );
        reservations.add(reservation);

        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId()))
                .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId().equals(id));

        if (!removed) {
            throw new InvalidReservationException("존재하지 않는 예약입니다.");
        }

        return ResponseEntity.noContent().build();
    }

    private void validateReservationRequest(ReservationRequest request) {
        try {
            if (request.getName() == null || request.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("예약자 이름은 필수입니다.");
            }
            if (request.getDate() == null || request.getDate().trim().isEmpty()) {
                throw new IllegalArgumentException("예약 날짜는 필수입니다.");
            }
            if (request.getTime() == null || request.getTime().trim().isEmpty()) {
                throw new IllegalArgumentException("예약 시간은 필수입니다.");
            }
        } catch (IllegalArgumentException e) {
            logger.error("예약 검증 실패: {}", e.getMessage(), e);
            throw e;
        }
    }
}
