package roomescape.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import roomescape.global.exception.InvalidReservationException;

public record RequestReservation(
        String date,
        String name,
        String time
) {

    public RequestReservation {
        validateEmpty(date, name, time);
    }

    private void validateEmpty(final String date, final String name, final String time) {
        if (date == null || date.isBlank() || name == null || name.isBlank() || time == null || time.isBlank()) {
            throw new InvalidReservationException("예약하기 위한 데이터(이름, 날짜, 시간)를 모두 입력해 주세요.");
        }
    }

    public LocalDate parseDate() {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidReservationException("날짜(년도-월-일)형식에 맞게 입력해 주세요. ex) 2020-12-31");
        }
    }

    public LocalTime parseTime() {
        try {
            return LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new InvalidReservationException("시간(시:분)형식에 맞게 입력해 주세요. ex) 15:30");
        }
    }

    public void validatePasted() {
        if (LocalDateTime.of(parseDate(), parseTime()).isBefore(LocalDateTime.now())) {
            throw new InvalidReservationException("이미 지난 날짜 및 시간은 예약할 수 없어요.");
        }
    }
}
