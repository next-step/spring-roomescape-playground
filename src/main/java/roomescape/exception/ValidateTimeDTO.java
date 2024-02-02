package roomescape.exception;

import roomescape.dto.TimeRequestDto;

import static io.micrometer.common.util.StringUtils.isBlank;

public class ValidateTimeDTO {
    public static void validateTime(TimeRequestDto time) {
        if (isBlank(time.time())) {
            throw new BadRequestException("time are required for reservation creation");
        }
    }
}
