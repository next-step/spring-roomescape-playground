package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import roomescape.exception.BadRequestException;

import java.time.LocalDate;

public record ReservationRequest(
        @NotBlank(message = "이름은 비어있을 수 없습니다.")
        String name,

        @NotNull(message = "날짜는 필수입니다.")
        LocalDate date,

        Long timeId
) {

    @JsonCreator
    public ReservationRequest(
            @JsonProperty("name") String name,
            @JsonProperty("date") LocalDate date,
            @JsonProperty("timeId") Long timeId,
            @JsonProperty("time") Object time
    ) {

this(
    name,
    date,
    convertToTimeId(time, timeId)
        );
}

private static Long convertToTimeId(Object time, Long timeId) {
    if (time instanceof String) {
        throw new BadRequestException("이제 시간은 문자열 형식을 지원하지 않습니다. 시간 ID를 입력해주세요.");
    }
    if (time instanceof Number) {
        return ((Number) time).longValue();
    }
    return timeId;
}
}
