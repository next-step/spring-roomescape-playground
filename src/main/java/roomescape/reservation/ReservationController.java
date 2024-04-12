package roomescape.reservation;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.exception.NotFoundReservationException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {

    private ReservationService reservationService;
    private List<ReservationDTO> reservationDTOs = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDTO>> read() {
        return ResponseEntity.ok().body(reservationDTOs);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDTO> create(@Valid @RequestBody ReservationDTO reservationDTO) {

        ReservationDTO newReservationDTO = reservationService.createReservationDTO(index.getAndIncrement(), reservationDTO);
        reservationDTOs.add(reservationDTO);

        return ResponseEntity.created(URI.create("/reservations/" + newReservationDTO.getId())).body(newReservationDTO);
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<ReservationDTO> update(@Valid @RequestBody ReservationDTO reservationDTO, @PathVariable long id) {
        ReservationDTO reservationDTOtoRemove = reservationDTOs.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservationDTOs.remove(reservationDTOtoRemove);

        ReservationDTO updatedReservationDTO = reservationService.updateReservationDTO(id, reservationDTO);
        reservationDTOs.add(updatedReservationDTO);

        return ResponseEntity.ok(updatedReservationDTO);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        ReservationDTO reservationDTO = reservationDTOs.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservationDTOs.remove(reservationDTO);

        return ResponseEntity.noContent().build();
    }

}
