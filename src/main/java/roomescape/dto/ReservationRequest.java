package roomescape.dto;

import roomescape.domain.Time;
import roomescape.exception.BlankReservationException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    private String name;
    private String date;
    private Time time;

    public LocalDate getParsedDate() {
        validateBlank();
        try {
            return LocalDate.parse(date);
        } catch (DateTimeException e) {
            throw new BlankReservationException("날짜나 시간의 형식이 올바르지 않습니다.");
        }
    }

    public LocalTime getParsedTime() {
        validateBlank();
        try {
            return LocalTime.parse(time);
        } catch (DateTimeException e) {
            throw new BlankReservationException("날짜나 시간의 형식이 올바르지 않습니다.");
        }
    }

    private void validateBlank() {
        if (date == null || date.isBlank() || time == null || time.isBlank()) {
            throw new BlankReservationException("날짜와 시간을 입력해주세요.");
        }
    }

    public ReservationRequest() {
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
