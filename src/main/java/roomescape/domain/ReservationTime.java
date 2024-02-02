package roomescape.domain;

import java.time.LocalTime;

import static java.util.Objects.isNull;

public class ReservationTime {

    private Long id;
    private LocalTime time;

    public ReservationTime(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
        validate(time);
    }

    private void validate(LocalTime time) {
        if (isNull(time)) {
            throw new IllegalArgumentException("예약 시간이 존재하지 않습니다.");
        }
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
