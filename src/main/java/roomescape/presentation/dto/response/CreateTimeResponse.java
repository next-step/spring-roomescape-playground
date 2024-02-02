package roomescape.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.application.dto.CreateInfoTimeDto;

import java.time.LocalTime;

public class CreateTimeResponse {

    private final Long id;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    private CreateTimeResponse(final Long id, final LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public static CreateTimeResponse from(final CreateInfoTimeDto createInfoTimeDto) {
        return new CreateTimeResponse(createInfoTimeDto.getId(), createInfoTimeDto.getTime());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
