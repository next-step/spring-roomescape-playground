package roomescape;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class DataController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    public DataController() {
        reservations.add(new Reservation(index.getAndIncrement(), "브라운", "2023-01-01", "10:00"));
        reservations.add(new Reservation(index.getAndIncrement(), "브라운", "2023-01-02", "11:00"));
        reservations.add(new Reservation(index.getAndIncrement(), "브라운", "2023-01-03", "12:00"));
    }

    @GetMapping("/reservations")
    public List<Reservation> reservations() {
        return reservations;
    }
}

