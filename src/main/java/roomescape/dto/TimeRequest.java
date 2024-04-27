package roomescape.dto;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TimeRequest {

    @NotNull(message = "시간을 입력해주세요.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;
}
