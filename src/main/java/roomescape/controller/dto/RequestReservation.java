package roomescape.controller.dto;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RequestReservation {
    private final LocalDate date;
    private final String name;
    private final Long timeId;

    public RequestReservation(final String date, final String name, final Long timeId) {
        validateEmpty(date, name, timeId);
        this.date = parseDate(date);
        this.name = name;
        this.timeId = timeId;
    }

    private void validateEmpty(final String date, final String name, final Long timeId) {
        if (date == null || date.isBlank() || name == null || name.isBlank() || timeId == null) {
            throw new IllegalArgumentException("예약하기 위한 데이터(이름, 날짜, 시간)를 모두 입력해 주세요.");
        }
    }

    private LocalDate parseDate(final String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("날짜(년도-월-일)형식에 맞게 입력해 주세요. ex) 2020-12-31");
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Long getTimeId() {
        return timeId;
    }
}
