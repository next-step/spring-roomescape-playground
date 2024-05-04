package roomescape.reservation.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.ReservationDTO;
import roomescape.reservation.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
public class ReservationController {

    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDTO>> read() {
        return ResponseEntity.ok().body(reservationService.read());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDTO> insert(@Valid @RequestBody ReservationDTO reservationDTO) {
        ReservationDTO insertedReservationDTO = reservationService.insertReservationDTO(reservationDTO);

        return ResponseEntity.created(URI.create("/reservations/" + insertedReservationDTO.getId())).body(insertedReservationDTO);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reservationService.deleteReservationDTO(id);

        return ResponseEntity.noContent().build();
    }

}
