package roomescape.controller.dto;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import roomescape.global.exception.InvalidValueException;

public record RequestReservation(
        String date,
        String name,
        Long timeId
) {

    public RequestReservation {
        validateEmpty(date, name, timeId);
    }

    private void validateEmpty(final String date, final String name, final Long timeId) {
        if (date == null || date.isBlank() || name == null || name.isBlank() || timeId == null) {
            throw new InvalidValueException("예약하기 위한 데이터(이름, 날짜, 시간)를 모두 입력해 주세요.");
        }
    }

    public LocalDate parseDate() {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidValueException("날짜(년도-월-일)형식에 맞게 입력해 주세요. ex) 2020-12-31");
        }
    }
}
