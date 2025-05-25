package roomescape.domain;

import lombok.Getter;

@Getter
public class Time {

    private final Long id;
    private final String time;

    public Time(Long id, String time) {
        if (time == null || time.isBlank()) {
            throw new IllegalArgumentException("시간은 필수입니다.");
        }
        this.id = id;
        this.time = time;
    }
}
