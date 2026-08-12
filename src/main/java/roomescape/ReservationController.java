package roomescape;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    @GetMapping("/reservation")
    public String reservations() {
        return "reservation";
    }

    private List<Reservation> reservations = new ArrayList<>();
}
