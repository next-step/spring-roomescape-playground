package roomescape.repository;

import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTimeRequest request);

}
