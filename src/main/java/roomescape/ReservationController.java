package roomescape;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("reservation")
    public String reservation(){
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read(){
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation){
        if( reservation.getName() == null ||
            reservation.getDate() == null ||
            reservation.getTimeNotFormatted() == null )
            throw new NotFoundReservationException();

        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/reservations/" + newReservation.getId()));

        return new ResponseEntity<>(newReservation, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Reservation reservation = reservations.stream()
            .filter(it -> Objects.equals(it.getId(), id))
            .findFirst()
            .orElseThrow(NotFoundReservationException::new);

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity handleException(NotFoundReservationException e){
        return ResponseEntity.badRequest().build();
    }
}

class NotFoundReservationException extends RuntimeException{
    public NotFoundReservationException(){}
    public NotFoundReservationException(String message){super(message);}
}