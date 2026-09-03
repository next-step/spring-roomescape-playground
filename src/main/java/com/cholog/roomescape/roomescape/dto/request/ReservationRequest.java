package com.cholog.roomescape.roomescape.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ReservationRequest(

        @NotBlank(message = "이름 필드는 null값일 수 없습니다.")
        @Size(max = 20, message = "이름은 20자를 넘길 수 없습니다.")
        String name,

        @NotNull(message = "예약 날짜는 null값일 수 없습니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotBlank(message = "예약 시각 기본 키는 비어 있는 값일 수 없습니다.")
        String time
) {
}
