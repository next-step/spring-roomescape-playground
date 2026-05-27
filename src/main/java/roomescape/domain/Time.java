package roomescape.domain;

import lombok.Getter;

import java.time.LocalTime;
import java.util.Objects;

@Getter
public class Time {
    private final Long id;
    private final LocalTime time;

    public Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    private void validate(String name, LocalTime time) {
        Objects.requireNonNull(name, "name은 필수입니다.");
        Objects.requireNonNull(time, "time은 필수입니다.");
    }
}
