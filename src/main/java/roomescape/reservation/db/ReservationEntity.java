package roomescape.reservation.db;

import lombok.Builder;
import lombok.Data;
import roomescape.time.db.TimeEntity;

@Data
@Builder
public class ReservationEntity {
    private Long id;
    private String name;
    private String date;
    private TimeEntity timeEntity;

}
