package roomescape.dao;

import java.util.List;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

public interface ReservationTimeDao {

    ReservationTime save(ReservationTime time);
    ReservationTime findById(Long id);
    List<ReservationTime> findAll();
    void deleteById(Long id);
}