package roomescape.dto.time.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalTime;

public record TimeResponse(
    Long id,
    @JsonFormat(shape = Shape.STRING, pattern = "HH:mm")
    LocalTime time
) {}
