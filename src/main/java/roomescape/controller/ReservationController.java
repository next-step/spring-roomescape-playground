package roomescape.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationResponse;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @GetMapping
    public List<ReservationResponse> showReservations() {
        return List.of(
                new ReservationResponse(
                        1L,
                        "브라운",
                        LocalDate.of(2023, 1, 1),
                        "10:00"
                ),
                new ReservationResponse(
                        2L,
                        "브라운",
                        LocalDate.of(2023, 1, 1),
                        "11:00"
                ),
                new ReservationResponse(
                        3L,
                        "브라운",
                        LocalDate.of(2023, 1, 1),
                        "12:00"
                )
        );
    }
}
