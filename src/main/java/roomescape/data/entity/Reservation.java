package roomescape.data.entity;

import java.sql.Time;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class Reservation {

    private Long id;
    private String name;
    private String date;
    private ReservationTime time;
}
