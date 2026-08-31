package com.cholog.roomescape.roomescape.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record TimeRequest(
        @NotNull(message = "시간 필드는 null값일 수 없습니다.")
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {
}
