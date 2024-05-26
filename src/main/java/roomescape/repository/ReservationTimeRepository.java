package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTimeRequest request);

    List<ReservationTime> findAll();

    void deleteById(Long id);

}
