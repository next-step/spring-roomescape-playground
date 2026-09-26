package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }

//    테스트용 데이터 추가
//    public ReservationController() {
//        reservations.add(new Reservation(1L,"가나다", LocalDate.of(2026,1,1), LocalTime.of(10,0)));
//        reservations.add(new Reservation(2L,"마바사", LocalDate.of(2026,1,1), LocalTime.of(10,0)));
//        reservations.add(new Reservation(3L,"abc", LocalDate.of(2026,1,1), LocalTime.of(10,0)));
//    }
}
