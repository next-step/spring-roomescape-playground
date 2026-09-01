package roomescape.domain;

import java.time.LocalTime;
import roomescape.exception.InvalidTimeRequestException;

public class ReservationTime {

    private final Long id;
    private final LocalTime time;

    public ReservationTime(Long id, LocalTime time) {
        if (time == null) {
            throw new InvalidTimeRequestException("시간은 필수입니다.");
        }
        this.id = id;
        this.time = time;
    }

    public ReservationTime withId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("시간 ID는 null일 수 없습니다.");
        }
        return new ReservationTime(id, time);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
