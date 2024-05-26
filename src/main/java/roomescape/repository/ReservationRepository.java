package roomescape.repository;

import java.util.List;
import roomescape.dto.ReservationRequest;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(ReservationRequest request);

    void deleteById(Long id);

}
