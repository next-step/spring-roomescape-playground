package roomescape.domain.reservationTime.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import roomescape.global.exception.RoomescapeBadRequestException;

public class ReservationTime {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

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

    public String formatTime() {
        return time.format(TIME_FORMATTER);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
