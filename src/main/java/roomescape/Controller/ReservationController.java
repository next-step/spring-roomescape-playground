package roomescape.Controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.DTO.ReservationRequestDTO;
import roomescape.DTO.ReservationResponseDTO;
import roomescape.Service.ReservationServiceImpl;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationServiceImpl service;

    public ReservationController(ReservationServiceImpl service)
    {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
        return ResponseEntity.ok(service.findAllReservations());
    }

    /*
    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable Long id)
    {
        return reservationDAO.findReservation(id);
    }
    */

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationRequestDTO reservationRequest) {
        Long id = service.createReservation(reservationRequest);
        ReservationResponseDTO newReservation = service.findReservationById(id);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        service.deleteReservationById(id);
        return ResponseEntity.noContent().build();
    }
}
