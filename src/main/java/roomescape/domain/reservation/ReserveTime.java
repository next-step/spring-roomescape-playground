package roomescape.domain.reservation;

import java.time.LocalTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReserveTime {

    private LocalTime value;

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
