package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation create(ReservationRequest reservationRequest);

    int delete(Long id);
}
