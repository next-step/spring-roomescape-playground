package roomescape.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.application.dto.ReadTimeDto;

import java.time.LocalTime;

public class ReadTimeResponse {

    private final Long id;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    private ReadTimeResponse(final Long id, final LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public static ReadTimeResponse from(final ReadTimeDto readTimeDto) {
        return new ReadTimeResponse(readTimeDto.getId(), readTimeDto.getTime());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
