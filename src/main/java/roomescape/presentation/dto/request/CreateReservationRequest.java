package roomescape.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import roomescape.configuration.ValidateDateFormat;
import roomescape.configuration.ValidateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CreateReservationRequest {

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static final String TIME_FORMAT_PATTERN = "HH:mm";

    @NotNull(message = "예약 날짜를 입력해주세요.")
    @ValidateDateFormat(message = "날짜 형식이 올바르지 않습니다.", pattern = DATE_FORMAT_PATTERN)
    private String date;

    @NotEmpty(message = "예약자 이름을 입력해주세요.")
    private String name;

    @NotNull(message = "예약 시간을 입력해주세요.")
    @ValidateTimeFormat(message = "시간 형식이 올바르지 않습니다.", pattern = TIME_FORMAT_PATTERN)
    private String time;

    public CreateReservationRequest(final String date, final String name, final String time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public LocalDate getDate() {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
    }

    public String getName() {
        return name;
    }

    public LocalTime getTime() {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN));
    }
}
