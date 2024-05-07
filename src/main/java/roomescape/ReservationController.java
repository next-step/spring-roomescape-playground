package roomescape;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@ControllerAdvice
class ReservationExceptionHandler {
    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<ResponseDto> handleException(NotFoundReservationException e) {
        ResponseDto errorResponse = new ResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(BadRequestCreateReservationException.class)
    public ResponseEntity<ResponseDto> handleException(BadRequestCreateReservationException e) {
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
    public ResponseEntity<ResponseDto> read() {
        ResponseDto response = new ResponseDto(HttpStatus.OK.value(), "요청이 성공적으로 처리되었습니다.", reservations );
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ResponseDto> create (@RequestBody ReservationRequestDto request) {
        String name = request.getName();
        String date = request.getDate();
        String time = request.getTime();

        if(StringUtils.isEmpty(name)) {
            throw new BadRequestCreateReservationException("The 'name' field is missing.");
        }
        if(StringUtils.isEmpty(date)) {
            throw new BadRequestCreateReservationException("The 'date' field is missing.");
        }
        if(StringUtils.isEmpty(time)) {
            throw new BadRequestCreateReservationException("The 'time' field is missing.");
        }

        long id = index.incrementAndGet();

        Reservation newReservation = new Reservation(id, name, date, time);
        reservations.add(newReservation);

        URI location = URI.create("/reservations/" + id);

        ResponseDto response = new ResponseDto(HttpStatus.CREATED.value(), "예약이 성공적으로 추가되었습니다.", newReservation);
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
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
