package roomescape;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReservationController {

    @GetMapping("/reservation")
    public void reservation () {
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        // 예약 목록 생성
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(1, "브라운", "2023-01-01", "10:00"));
        reservations.add(new Reservation(2, "브라운", "2023-01-02", "11:00"));

        // 응답 생성 및 반환
        return ResponseEntity.status(HttpStatus.OK).body(reservations);
    }
}
