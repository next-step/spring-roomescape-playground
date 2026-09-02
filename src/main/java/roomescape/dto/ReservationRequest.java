package roomescape.dto;

import roomescape.exception.BlankReservationException;

import java.time.DateTimeException;
import java.time.LocalDate;

public class ReservationRequest {
    private String name;
    private String date;
    private Long time;

    public LocalDate getParsedDate() {
        validateBlank();
        try {
            return LocalDate.parse(date);
        } catch (DateTimeException e) {
            throw new BlankReservationException("날짜나 시간의 형식이 올바르지 않습니다.");
        }
    }

    private void validateBlank() {
        if (date == null || date.isBlank() || time == null) {
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

    public Long getTime() {
        return time;
    }
}
