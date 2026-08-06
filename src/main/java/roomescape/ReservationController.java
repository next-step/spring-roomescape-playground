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
    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationController() {
        reservations.add(new Reservation(
                1L, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)
        ));
        reservations.add(new Reservation(
                2L, "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0)
        ));
        reservations.add(new Reservation(
                3L, "브라운", LocalDate.of(2023, 1, 3), LocalTime.of(12, 0)
        ));
    }
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> readReservationList() {
        List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }
}
