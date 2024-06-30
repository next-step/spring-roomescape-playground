package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;


@Controller
public class ReservationController {
    private final List<Reservation> reservations= new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public ReservationController() {
        reservations.add(new Reservation(index.getAndIncrement(), "김혜준", "2023-01-01", "10:00"));
        reservations.add(new Reservation(index.getAndIncrement(), "김혜준", "2023-01-02", "11:00"));
        reservations.add(new Reservation(index.getAndIncrement(), "김혜준", "2023-01-03", "12:00"));
    }

    // reservation HTML 연결
    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> reservations(){
        return ResponseEntity.ok(reservations);
    }


}

