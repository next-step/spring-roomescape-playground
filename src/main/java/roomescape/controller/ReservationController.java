package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;

@Controller
public class ReservationController {

    private final AtomicLong id = new AtomicLong(1L);
    private final List<Reservation> reservations = List.of(
        new Reservation(id.getAndAdd(1L), "브라운", LocalDate.now(), LocalTime.now()),
        new Reservation(id.getAndAdd(1L), "브리", LocalDate.now(), LocalTime.now()),
        new Reservation(id.getAndAdd(1L), "주노", LocalDate.now(), LocalTime.now())
    );

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation.html";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> reservations() {
        return reservations.stream()
            .map(ReservationResponse::from)
            .toList();
    }
}
