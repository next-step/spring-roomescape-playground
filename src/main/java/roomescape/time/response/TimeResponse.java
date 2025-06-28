package roomescape.time.response;

import java.time.format.DateTimeFormatter;
import roomescape.time.model.Time;

public class TimeResponse {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final Long id;
    private final String time;

    public TimeResponse(Time time) {
        this.id = time.getId();
        this.time = time.getTime().format(TIME_FORMATTER);
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }


}

