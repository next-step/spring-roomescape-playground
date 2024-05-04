package roomescape;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@ControllerAdvice
class ReservationExceptionHandler {
    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity handleException(NotFoundReservationException e) {
        String errorMessage = e.getMessage();
        ResponseDto errorResponse = new ResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}


@Controller
public class ReservationController {

    @GetMapping("/reservation")
    public void reservation () {
    }

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
//        reservations.add(new Reservation(1,"브라운", "2023-01-01", "10:00"));
//        reservations.add(new Reservation(2,"브라운", "2023-01-02", "11:00"));

        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create (@RequestBody ReservationRequestDto request) {
        long id = index.incrementAndGet();

        Reservation newReservation = new Reservation(id, request.getName(), request.getDate(), request.getTime());
        reservations.add(newReservation);

        URI location = URI.create("/reservations/" + id);

        return ResponseEntity.created(location).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity delete (@PathVariable Long id) {
        Iterator<Reservation> iterator = reservations.iterator();

        while(iterator.hasNext()) {
            Reservation reservation = iterator.next();
            if(reservation.getId() == id) {
                iterator.remove();
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }

        throw new NotFoundReservationException("Reservation with id " + id + " not found." );
    }
}
