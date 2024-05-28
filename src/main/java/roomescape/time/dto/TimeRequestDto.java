package roomescape.time.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public record TimeRequestDto(
        @JsonFormat(pattern = "HH:mm") LocalTime time
) {
}
