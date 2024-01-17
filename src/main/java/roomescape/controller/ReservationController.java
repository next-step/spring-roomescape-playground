package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.persistence.ReservationRepository;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<ReservationDto> reservations = this.reservationRepository.findAll();

        return ResponseEntity.ok()
                .body(reservations);
    }

    @PostMapping
    public ResponseEntity<ReservationDto> createReservations(@RequestBody Map<String, String> reservationRequest) {
        ReservationDto reservation = reservationRepository.save(reservationRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + reservation.id())
                .body(reservation);
    }

    @DeleteMapping("/{deleteId}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "deleteId") long deleteId) {
        reservationRepository.deleteById(deleteId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
