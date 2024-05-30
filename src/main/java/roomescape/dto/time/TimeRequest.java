package roomescape.dto.time;

import roomescape.domain.TimeDomain;
import roomescape.exception.BadReservationSaveException;

public record TimeRequest(
        Long id,
        String time
) {
    private final static String timePattern = "\\d{2}:\\d{2}";

    public TimeRequest {
        validate(time);
    }


    private void validate(String time) {
        validateBlank(time);
        validateFormat(time);
    }

    private void validateBlank(String time) {
        if (time.isBlank()) {
            throw new BadReservationSaveException("빈 값이 입력되었습니다.");
        }
    }

    private void validateFormat(String time) {
        if (!time.matches(timePattern)) {
            throw new BadReservationSaveException("시간 형식이 올바르지 않습니다. (형식: HH:MM)");
        }
    }

    public TimeDomain makeTime() {
        return new TimeDomain(time);
    }
}
