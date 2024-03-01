package roomescape.domain.time.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

public class TimeCreateDTO {

    @NotNull(message = "시간을 입력해주세요.")
    private LocalTime time;

    TimeCreateDTO() {

    }

    TimeCreateDTO(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}
