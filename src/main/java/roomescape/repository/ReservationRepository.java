package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;

public interface ReservationRepository {
    List<ReservationResponse> findAll();

    ReservationResponse save(Reservation reservation);

    int deleteById(Long id);
}
