package roomescape.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationQueryController {
    private final ReservationRepository jdbcReservationRepository;

    public ReservationQueryController(final ReservationRepository jdbcReservationRepository) {
        this.jdbcReservationRepository = jdbcReservationRepository;
    }

    @GetMapping
    public List<Reservation> getReservations() {
        return jdbcReservationRepository.findAll();
    }
}
