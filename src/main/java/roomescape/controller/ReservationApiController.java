package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationResponse;

@RestController
public class ReservationApiController {
    @GetMapping("/reservations")
    public List<ReservationResponse> getReservations() {
        return List.of(
                new ReservationResponse(1L, "햄버거", "2025-06-17", "10:00"),
                new ReservationResponse(2L, "감자튀김", "2025-06-17", "11:00"),
                new ReservationResponse(3L, "콜라", "2025-06-17", "12:00")
        );
    }
}
