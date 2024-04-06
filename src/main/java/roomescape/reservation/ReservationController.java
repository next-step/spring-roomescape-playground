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

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservation")
    public String world() {
        return "reservation";
    }

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
    @ResponseBody
    public ResponseEntity<ReservationDTO> update(@PathVariable long id, @RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservation.setName(reservationDTO.getName());
        reservation.setDate(reservationDTO.getDate());
        reservation.setTime(reservationDTO.getTime());

        return ResponseEntity.ok(ReservationDTOMapper.toDTO(reservation));
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
