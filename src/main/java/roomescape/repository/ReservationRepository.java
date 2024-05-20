package roomescape.repository;

import java.util.List;
import roomescape.dto.ReservationRequest;
import roomescape.model.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(ReservationRequest request);

    void deleteById(Long id);

}
