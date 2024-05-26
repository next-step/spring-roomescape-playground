package roomescape.repository;

import java.util.List;
import roomescape.dto.ReservationRequest;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(ReservationRequest request, Long timeId);

    void deleteById(Long id);

}
