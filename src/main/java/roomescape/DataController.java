package roomescape;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class DataController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    public DataController() {
        reservations.add(new Reservation(index.getAndIncrement(), "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)));
        reservations.add(new Reservation(index.getAndIncrement(), "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0)));
        reservations.add(new Reservation(index.getAndIncrement(), "브라운", LocalDate.of(2023, 1, 3),LocalTime.of(12, 0)));
    }

    @GetMapping("/reservations")
    public List<Reservation> reservations() {
        return reservations;
    }
}

