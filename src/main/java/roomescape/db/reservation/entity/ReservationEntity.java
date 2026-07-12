package roomescape.db.reservation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import roomescape.db.time.entity.TimeEntity;

@Getter
@Builder
@AllArgsConstructor
public class ReservationEntity {

    private final long id;

    private final String name;

    private final String date;

    private final TimeEntity time;

}
