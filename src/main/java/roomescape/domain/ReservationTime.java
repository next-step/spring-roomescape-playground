package roomescape.domain;

import java.time.LocalTime;
import roomescape.exception.ReservationInvalidException;

public class ReservationTime {
    private final Long id;
    private final LocalTime time;

    private ReservationTime(Long id, LocalTime time) {
        if (time == null) {
            throw new ReservationInvalidException("예약 시간은 비어 있을 수 없습니다");
        }

        this.id = id;
        this.time = time;
    }

    public static ReservationTime createNewReservationTime(LocalTime time) {
        return new ReservationTime(null, time);
    }

    public static ReservationTime createFromPersistedData(Long id, LocalTime time) {
        if (id == null) {
            throw new ReservationInvalidException("id는 비어 있을 수 없습니다");
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
