package roomescape.dto;

import roomescape.exception.InvalidReservationException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public record ReservationRequest(
        String name,
        String date,
        String time
) {
    public LocalDate toDate() {
        validate();
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidReservationException("날짜 형식이 올바르지 않습니다. date=" + date);
        }
    }

    public Long toTimeId() {
        validate();

        long timeId;
        try {
            timeId = Long.parseLong(time);
        } catch (NumberFormatException e) {
            throw new InvalidReservationException("시간 식별자는 숫자여야 합니다. time=" + time);
        }

        validatePositive(timeId);
        return timeId;
    }

    private void validate() {
        if (isBlank(name) || isBlank(date) || isBlank(time)) {
            throw new InvalidReservationException("예약에 필요한 인자가 없습니다.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private void validatePositive(long timeId) {
        if (timeId <= 0) {
            throw new InvalidReservationException("시간 식별자는 1 이상이어야 합니다. time=" + time);
        }
    }
}
