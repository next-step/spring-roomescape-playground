package roomescape.dto;

import roomescape.exception.ReservationException;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public record TimeRequest(String time) {

    public LocalTime toLocalTime() {
        if (time == null || time.isBlank()) {
            throw new ReservationException("[ERROR] 시간은 비어있을 수 없습니다.");
        }
        try {
            return LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new ReservationException("[ERROR] 시간 형식이 올바르지 않습니다. (HH:mm)");
        }
    }
}
