package roomescape.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.dto.ReservationResponse;
import roomescape.domain.repository.ReservationRepository;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationQueryController {
    private final ReservationRepository jdbcReservationRepository;

    public ReservationQueryController(final ReservationRepository jdbcReservationRepository) {
        this.jdbcReservationRepository = jdbcReservationRepository;
    }

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return ReservationResponse.from(jdbcReservationRepository.findAll());
    }
}
