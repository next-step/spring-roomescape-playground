package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.time.Clock;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.exception.ReservationNotFoundException;
import roomescape.repository.ReservationRepository;

@Controller
public class ReservationController {
    private final Clock clock;

    private final ReservationRepository reservationRepository;

    public ReservationController(Clock clock, ReservationRepository reservationRepository) {
        this.clock = clock;
        this.reservationRepository = reservationRepository;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationRequest reservationRequest) {
        Reservation temporaryReservation = Reservation.create(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time(),
                clock);

        Reservation savedReservation = reservationRepository.save(temporaryReservation);
        return ResponseEntity.created(URI.create("/reservations/" + savedReservation.getId())).body(savedReservation);
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        int deletedRows = reservationRepository.deleteById(reservationId);
        if (deletedRows == 0) {
            throw new ReservationNotFoundException("id " + reservationId + "에 해당하는 예약을 찾을 수 없습니다.");
        }
        return ResponseEntity.noContent().build();
    }
}
