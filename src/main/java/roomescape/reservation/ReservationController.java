package roomescape.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    ReservationService reservationService;
    List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String world() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ReservationDTO> create(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO newReservationDTO = new ReservationDTO(index.getAndIncrement(), reservationDTO.getName(), reservationDTO.getDate(), reservationDTO.getTime());
        Reservation newReservation = ReservationDTOMapper.toEntity(newReservationDTO);
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservationDTO.getId())).body(newReservationDTO);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }


}
