package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {

    private Long id;
    private LocalTime time;

    public ReservationTime(final Long id, final LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public ReservationTime(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

}
