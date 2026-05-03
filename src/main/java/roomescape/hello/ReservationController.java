package roomescape.hello;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public ReservationController() {
        // 초기 데이터 추가
        reservations.add(new Reservation(counter.incrementAndGet(), "브라운", "2023-01-01", "10:00"));
        reservations.add(new Reservation(counter.incrementAndGet(), "브라운", "2023-01-02", "11:00"));
        reservations.add(new Reservation(counter.incrementAndGet(), "브라운", "2023-01-03", "12:00"));
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservations;
    }
}
