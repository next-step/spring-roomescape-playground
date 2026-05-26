package roomescape.timeslot.model;

import java.time.LocalTime;

public class Timeslot {
    private Long id;
    private LocalTime timeslot;

    public Timeslot(Long id, LocalTime timeslot) {
        this.id = id;
        this.timeslot = timeslot;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTimeslot() {
        return timeslot;
    }
}
