package roomescape.reservation;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private final AtomicLong index = new AtomicLong();
    private List<Reservation> reservations = new ArrayList<>();
    
    @PostConstruct
    void init() {
        reservations.add(new Reservation(index.incrementAndGet(), "브라운", LocalDate.now(), LocalTime.now()));
        reservations.add(new Reservation(index.incrementAndGet(), "SEOKJU", LocalDate.now(), LocalTime.now()));
        reservations.add(new Reservation(index.incrementAndGet(), "HONG", LocalDate.now(), LocalTime.now()));
    }

    @GetMapping("/reservation")
    public String getReservationPage() {
        return "reservation";
    }
    
    @ResponseBody
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservationList() {
        return ResponseEntity.ok(reservations);
    }
}
