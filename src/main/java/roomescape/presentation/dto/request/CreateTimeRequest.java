package roomescape.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import roomescape.configuration.ValidateTimeFormat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CreateTimeRequest {

    private static final String TIME_FORMAT_PATTERN = "HH:mm";

    @NotNull(message = "시간을 입력해주세요.")
    @ValidateTimeFormat(message = "시간 형식이 올바르지 않습니다.", pattern = TIME_FORMAT_PATTERN)
    private String time;

    public CreateTimeRequest() {
    }

    public CreateTimeRequest(final String time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN));
    }
}
