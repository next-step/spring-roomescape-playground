package roomescape.domain.reservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotNull(message = "날짜를 입력해주세요.")
    private LocalDate date;

    @NotNull(message = "시간을 입력해주세요.")
    private LocalTime time;

    public ReservationRequest() {}

    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
}
