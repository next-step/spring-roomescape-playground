package roomescape.time.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public record TimeResponseDto(
        long id,
        @JsonFormat(pattern = "HH:mm") LocalTime time
) {
}
