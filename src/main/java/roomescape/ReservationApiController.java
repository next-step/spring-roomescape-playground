package roomescape;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationApiController {

        @GetMapping("/reservations")
        public List<Reservations> getReservations() {
                List<Reservations> reservations = new ArrayList<>();
                reservations.add (new Reservations(1L,"브라운","2023-01-01","10:00"));
                reservations.add (new Reservations(2L,"브라운","2023-01-02","10:00"));
                reservations.add (new Reservations(3L,"브라운","2023-01-03","10:00"));

                return reservations;
        };
}
