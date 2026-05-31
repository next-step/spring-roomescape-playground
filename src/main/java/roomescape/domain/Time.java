package roomescape.domain;

import lombok.Getter;

import java.time.LocalTime;
import java.util.Objects;

@Getter
public class Time {
    private final Long id;
    private final LocalTime time;

    public Time(Long id, LocalTime time) {
        validate(id, time);
        this.id = id;
        this.time = time;
    }

    private void validate(Long id, LocalTime time) {
        Objects.requireNonNull(time, "time은 필수입니다.");
    }
}
