package roomescape.dto;

import roomescape.exception.ReservationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public record ReservationRequest(String date, String name, String time) {

    public LocalDate parseDate() {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new ReservationException("[ERROR] 날짜 형식이 잘못되었습니다.");
        }
    }

    public LocalTime parseTime() {
        try {
            return LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new ReservationException("[ERROR] 시간 형식이 잘못되었습니다.");
        }
    }
}
