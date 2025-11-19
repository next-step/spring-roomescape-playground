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
import roomescape.repository.ReservationRepository;

import java.util.List;
import java.net.URI;

@Controller
public class ReservationController {

    private final ReservationRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    public ReservationController(ReservationRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return repository.findAll();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest request) {
        validateReservationRequest(request);

        Reservation reservation = repository.save(request);

        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId()))
                .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean removed = repository.deleteById(id);

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
