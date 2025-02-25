package roomescape.dto.time.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalTime;

public record TimeCreateRequest(
    @JsonFormat(shape = Shape.STRING, pattern = "HH:mm")
    LocalTime time
) {}
