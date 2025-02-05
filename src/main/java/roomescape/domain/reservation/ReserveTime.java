package roomescape.domain.reservation;

import java.time.LocalTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class ReserveTime {

    private final LocalTime value;

    public ReserveTime(LocalTime reserveTime) {
        valid(reserveTime);
        value = reserveTime;
    }

    private void valid(LocalTime reserveTime) {
        if (Objects.isNull(reserveTime)) {
            throw new IllegalArgumentException();
        }
    }
}
