package roomescape.repository;

import java.util.List;
import roomescape.controller.ReservationTimeController;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTimeRequest request);

    List<ReservationTime> findAll();

}
