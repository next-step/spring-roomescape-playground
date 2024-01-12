package roomescape.application;

import org.springframework.stereotype.Service;
import roomescape.application.dto.ReservationResponse;
import roomescape.domain.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationQueryService {

    private final ReservationRepository jdbcReservationRepository;

    public ReservationQueryService(final ReservationRepository jdbcReservationRepository) {
        this.jdbcReservationRepository = jdbcReservationRepository;
    }

    public List<ReservationResponse> findReservations() {
        return ReservationResponse.from(jdbcReservationRepository.findAll());
    }
}
