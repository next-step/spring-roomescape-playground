package roomescape.domain.time.dto.request;

import jakarta.validation.constraints.NotBlank;
import roomescape.domain.time.entity.Time;

public record TimeCreateRequestDto(@NotBlank(message = "시간이 공백입니다.") String time) {
    public Time toEntity(Long id) {
        return new Time(id, time);
    }
}