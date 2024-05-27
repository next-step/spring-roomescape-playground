package roomescape.reservation.domain;

import lombok.Data;
import roomescape.time.domain.Time;

@Data
public class Reservation {
    private Long id;
    private String name;
    private String date;
    private Time time;

}