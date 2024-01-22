package roomescape.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationRequest {

    @NotNull(message = "예약 날짜를 입력해주세요.")
    private LocalDate date;

    @NotEmpty(message = "예약자 이름을 입력해주세요.")
    private String name;

    @NotNull(message = "예약 시간을 입력해주세요.")
    private LocalTime time;

    public CreateReservationRequest(final LocalDate date, final String name, final LocalTime time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public LocalTime getTime() {
        return time;
    }
}
