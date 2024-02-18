package roomescape.data.dto;

import java.sql.Time;
import lombok.Getter;
import roomescape.data.entity.ReservationTime;

@Getter
public class ReservationRequest {

    private String date;
    private String name;
    private long timeId;
}
