package roomescape.time.presentation.dto;

import java.util.Arrays;
import roomescape.time.domain.Time;
import roomescape.time.exception.BadTimeAddException;

public record AddTimeRequest(String time) {

    public AddTimeRequest {
        validate(time);
    }

    private void validate(String time) {
        validateBlanks(time);
    }

    private void validateBlanks(String... fields) {
        boolean isBlankFieldExist = Arrays.stream(fields)
                                          .anyMatch(String::isBlank);
        if (isBlankFieldExist) {
            throw new BadTimeAddException("빈 값이 입력되었습니다.");
        }
    }

    public Time toTime() {
        return new Time(null, time);
    }
}
