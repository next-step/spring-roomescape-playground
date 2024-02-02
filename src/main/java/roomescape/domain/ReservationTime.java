package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {

    private Long id;
    private LocalTime time;

    public ReservationTime(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public ReservationTime(Long id, String time) {
        this(id, LocalTime.parse(time));
    }

    public ReservationTime(String time) {
        this(null, LocalTime.parse(time));
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
