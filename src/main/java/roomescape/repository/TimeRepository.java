package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface TimeRepository {

    List<ReservationTime> findAll();

    ReservationTime save(ReservationTime reservationTime);

    boolean deleteById(Long id);
}
