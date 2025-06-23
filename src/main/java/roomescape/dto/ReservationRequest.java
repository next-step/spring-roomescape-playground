package roomescape.dto;

import roomescape.exception.ReservationException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public record ReservationRequest(String name, String date, Long timeId) {

    public ReservationRequest {
        if (timeId == null) {
            throw new ReservationException("[ERROR] 시간 ID는 비어있을 수 없습니다.");
        }
    }

    public String getValidatedName() {
        if (name == null || name.isBlank()) {
            throw new ReservationException("[ERROR] 이름은 비어있을 수 없습니다.");
        }
        return name;
    }

    public LocalDate getParsedDate() {
        if (date == null || date.isBlank()) {
            throw new ReservationException("[ERROR] 날짜는 비어있을 수 없습니다.");
        }
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new ReservationException("[ERROR] 날짜 형식이 잘못되었습니다. (yyyy-MM-dd)");
        }
    }
}
