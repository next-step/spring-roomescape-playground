package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReadReservationResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class ReservationController {

    private List<Reservation> reservations = List.of(
            new Reservation(1L, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)),
            new Reservation(2L, "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0)),
            new Reservation(3L, "브라운", LocalDate.of(2023, 1, 3), LocalTime.of(12, 0))
    );

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation.html";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReadReservationResponse>> readAll() {
        final List<ReadReservationResponse> responses = reservations.stream()
                                                                    .map(ReadReservationResponse::from)
                                                                    .toList();

        return ResponseEntity.ok(responses);
    }
}
