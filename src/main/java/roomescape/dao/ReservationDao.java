package roomescape.dao;

import java.util.List;
import roomescape.dto.ReservationRequest;
import roomescape.domain.Reservation;

public interface ReservationDao {

    List<Reservation> findAll();

    Reservation save(ReservationRequest request, Long timeId);

    void deleteById(Long id);

}
