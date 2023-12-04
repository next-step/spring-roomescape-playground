package roomescape.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class Time {
    private final Long id;
    @NotBlank(message = "시간은 반드시 입력해야 합니다.")
    private final String time;

    public Time(long id, String time) {
        this.id = id;
        this.time =time;
    }

    public static Time of(long id, String time) {
        return new Time(id, time);
    }
}
