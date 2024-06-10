package roomescape.application;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

public interface ReservationService {

    List<Reservation> findAll();
    Reservation save(ReservationRequest request);
    void deleteById(Long id);
}
