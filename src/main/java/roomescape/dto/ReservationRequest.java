package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import roomescape.exception.BadRequestException;

import java.time.LocalDate;

public record ReservationRequest(
        //이름을 비워서 보낼 수 있음
        String name,

        @NotNull(message = "날짜는 필수입니다.")
        LocalDate date,

        Long time,
        Long theme
) {

    @JsonCreator
    public ReservationRequest(
            @JsonProperty("name") String name,
            @JsonProperty("date") LocalDate date,
            @JsonProperty("timeId") Long timeId,
            @JsonProperty("time") Object time,
            @JsonProperty("theme") Long theme
    ) {
        this(
                name,
                date,
                convertToTimeId(time, timeId),
                theme
        );
}

    private static Long convertToTimeId(Object time, Long timeId) {
        if (time instanceof String StringTime) {
            if (StringTime.contains(":")) {
                throw new BadRequestException("이제 시간은 문자열 형식을 지원하지 않습니다. 시간 ID를 입력해주세요.");
            }
            try {
                return Long.parseLong(StringTime);
            } catch (NumberFormatException e) {
                throw new BadRequestException("유효하지 않은 시간 ID 형식입니다.");
            }
        }
        if (time instanceof Number) {
            return ((Number) time).longValue();
        }
        return timeId;
    }
}
