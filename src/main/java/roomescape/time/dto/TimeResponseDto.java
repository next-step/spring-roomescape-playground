package roomescape.time.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public record TimeResponseDto(
        int id,
        @JsonFormat(pattern = "HH:mm") LocalTime time
) {
}
