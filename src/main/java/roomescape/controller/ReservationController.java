package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.dto.ReservationRequestDto;
import roomescape.domain.dto.ReservationResponseDto;
import roomescape.service.ReservationService;

import java.util.List;

@Controller
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }

    // JDBC
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto.Get>> getReservation() {
        return ResponseEntity.ok(reservationService.findAllReservation());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto.Create> create(@Valid @RequestBody ReservationRequestDto.Create request) throws Exception {
        ReservationResponseDto.Create createdReservation = reservationService.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/reservations/" + createdReservation.getId()).body(createdReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
