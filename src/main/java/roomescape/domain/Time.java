package roomescape.domain;

import roomescape.dto.TimeRequestDto;
import roomescape.exception.BadRequestException;

import static io.micrometer.common.util.StringUtils.isBlank;

public class Time {
    private final Long id;
    private final String time;

    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
        validateTime(time);
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    private void validateTime(String time) {
        if (isBlank(time)) {
            throw new BadRequestException("time are required for reservation creation");
        }
    }
}
