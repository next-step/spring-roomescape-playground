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
@ControllerAdvice
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
        if (reservationDTO.getName() == null || reservationDTO.getName().trim().isEmpty() ||
                reservationDTO.getDate() == null || reservationDTO.getDate().trim().isEmpty() ||
                reservationDTO.getTime() == null || reservationDTO.getTime().trim().isEmpty()) {
            throw new IllegalArgumentException("Reservation name, date, and time must be provided");
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

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity handleException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        // BadRequest(400) 상태 코드와 함께 예외 메시지 반환
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
