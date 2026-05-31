package roomescape.time.domain;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.Objects;

public record CreateTimeInfo(@NotNull LocalTime time) {
    public CreateTimeInfo {
        Objects.requireNonNull(time, "time이 null일 수 없습니다.");

        if (time.getSecond() != 0 || time.getNano() != 0)
            throw new TimeException.IllegalPrecision();
    }
}
