package roomescape;

import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {

    private final Long id;
    private final LocalTime time;

    public ReservationTime(Long id, LocalTime time) {
        this.id = id;
        this.time = Objects.requireNonNull(
                time,
                "예약 시간은 필수입니다."
        );
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
