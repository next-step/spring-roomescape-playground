package roomescape.time.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class Time {

    private Long id;
    private LocalTime time;

    public Time(Long id, LocalTime time) {
        this.id = id;
        this.time = Objects.requireNonNull(time, "시간은 필수 입력값입니다.");
    }
}
