package roomescape.domain.reservationTime.domain;

import java.time.LocalTime;
import roomescape.global.exception.RoomescapeBadRequestException;

public class ReservationTime {

    private final Long id;

    private final LocalTime time;

    public ReservationTime(final Long id, final LocalTime time) {
        if (time == null) {
            throw new RoomescapeBadRequestException("시간은 필수입니다.");
        }
        this.id = id;
        this.time = time;
    }

    public ReservationTime(final LocalTime time) {
        this(null, time);
    }

    public boolean isBefore() {
        return this.time.isBefore(LocalTime.now());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
