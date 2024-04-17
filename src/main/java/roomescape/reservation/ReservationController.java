package roomescape.reservation;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class ReservationController {

    private ReservationService reservationService;

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

//    @PutMapping("/reservations/{id}")
//    public ResponseEntity<ReservationDTO> update(@Valid @RequestBody ReservationDTO reservationDTO, @PathVariable long id) {
//        reservationService.updateReservationDTO(id, reservationDTO);
//
//        return ResponseEntity.ok(reservationDTO);
//    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reservationService.deleteReservationDTO(id);

        return ResponseEntity.noContent().build();
    }

}
