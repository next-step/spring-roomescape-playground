package roomescape;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static roomescape.ErrorMessage.*;


@Controller
public class ReservationController {
    private final List<Reservation> reservations= new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public ReservationController() {
    }

    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> reservations(){
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        validateReservation(reservation);
        Reservation newReservation = new Reservation(
                index.getAndIncrement(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation);
    }

    private void validateReservation(Reservation reservation) {
        if(reservation.getName() == null || reservation.getName().isEmpty()) {
            throw new NotFoundReservationException(NOT_INPUT_NAME.message);
        }else if(reservation.getDate() == null || reservation.getDate().isEmpty()) {
            throw new NotFoundReservationException(NOT_INPUT_DATE.message);
        }else if(reservation.getTime() == null || reservation.getTime().isEmpty()) {
            throw new NotFoundReservationException(NOT_INPUT_TIME.message);
        }
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException(NOT_FOUND_RESERVATION.message));

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<String> handleInvalidReservationException(NotFoundReservationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}

