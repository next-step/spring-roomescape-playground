package roomescape.reservation;

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

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDTO> create(@RequestBody ReservationDTO reservationDTO) {
        if (reservationDTO.getName() == null || reservationDTO.getName().trim().isEmpty() ||
                reservationDTO.getDate() == null || reservationDTO.getDate().trim().isEmpty() ||
                reservationDTO.getTime() == null || reservationDTO.getTime().trim().isEmpty()) {
            throw new IllegalArgumentException("값이 비어 있음.");
        }

        ReservationDTO newReservationDTO = new ReservationDTO(index.getAndIncrement(), reservationDTO.getName(), reservationDTO.getDate(), reservationDTO.getTime());
        Reservation newReservation = ReservationDTOMapper.toEntity(newReservationDTO);
        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservationDTO.getId())).body(newReservationDTO);
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<ReservationDTO> update(@PathVariable long id, @RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservations.remove(reservation);
        Reservation updatedReservation = new Reservation(id, reservationDTO.getName(), reservationDTO.getDate(), reservationDTO.getTime());
        reservations.add(updatedReservation);

        ReservationDTO newReservationDTO = ReservationDTOMapper.toDTO(updatedReservation);

        return ResponseEntity.ok(newReservationDTO);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }

}
