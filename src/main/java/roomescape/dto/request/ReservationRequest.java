package roomescape.dto.request;

import java.sql.Time;
import java.util.Date;

public class ReservationRequest {

    private String name;
    private String date;
    private String time;

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
