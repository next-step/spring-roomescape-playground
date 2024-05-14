package roomescape;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    private ReservationQueryingDAO queryingDAO;

    public ReservationController(ReservationQueryingDAO queryingDAO) {
        this.queryingDAO = queryingDAO;
    }

    @GetMapping("/reservation")
    public void reservation () {
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
//        테스트 코드를 위해 기존 ResponseDto를 이용한 코드 주석 처리
//        ResponseDto response = new ResponseDto(HttpStatus.OK.value(), "요청이 성공적으로 처리되었습니다.", queryingDAO.getReservations() );
//        return ResponseEntity.ok().body(response);
        return ResponseEntity.ok(queryingDAO.getReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ResponseDto> create (@RequestBody ReservationRequestDto request) {
        String name = request.getName();
        String date = request.getDate();
        String time = request.getTime();

        ReservationRequestDto newReservation = new ReservationRequestDto(name, date, time);

        long id = queryingDAO.createReservation(newReservation);

        URI location = URI.create("/reservations/" + id);

        ResponseDto response = new ResponseDto(HttpStatus.CREATED.value(), "예약이 성공적으로 추가되었습니다.", newReservation);
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        int rowsAffected = queryingDAO.deleteReservationById(id);

        if(rowsAffected > 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            throw new NotFoundReservationException("Reservation with id " + id + " not found." );
        }
    }
}
