package roomescape.time.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record TimeRequest(
        @JsonFormat(shape = Shape.STRING, pattern = "HH:mm")
        @NotNull(message = "시간이 비어 있을 수 없습니다.")
        LocalTime time) {
}
