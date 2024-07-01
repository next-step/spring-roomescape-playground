package roomescape;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoomescapeController {

    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservation")
    public String reservation() {

        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> reservations() {
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation( @RequestBody Reservation reservation) {
        Reservation newReservation = new Reservation(reservations.size() + 1, reservation.getName(), reservation.getDate(), reservation.getTime());
        if (reservation.getName() == null || reservation.getName().trim().isEmpty() ||
                reservation.getDate() == null || reservation.getDate().trim().isEmpty() ||
                reservation.getTime() == null || reservation.getTime().trim().isEmpty()) {
            throw new IllegalArgumentException("예약 정보가 부족합니다.");
        }
        reservations.add(newReservation);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/reservations/" + newReservation.getId());
        return new ResponseEntity<>(newReservation,headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Reservation> deleteReservation(@PathVariable int id){
        for (Reservation reservation : reservations) {
            if (reservation.getId() == id) {
                reservations.remove(reservation);
                return new ResponseEntity<>(reservation, HttpStatus.NO_CONTENT);
            }
        }
        // 예약이 없는 경우 Exception 발생
        throw new IllegalArgumentException("삭제할 예약이 없습니다.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
