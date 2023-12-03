package roomescape.time.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import roomescape.time.domain.Time;

import java.time.LocalTime;

public class TimeRequest {
    public record CreateTimeDto(
            @NotNull(message = "시간을 입력해주세요.")
            @JsonFormat(pattern = "HH:mm")
            LocalTime time
    ){
        public Time toEntity() {
            return Time.builder()
                    .time(time)
                    .build();
        }
    }
}
