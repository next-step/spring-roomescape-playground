package roomescape.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ReservationReq {

    @NotBlank(message = "예약자 이름을 입력해주세요.")
    private String name;

    @NotNull(message = "예약 날짜를 입력해주세요.")
    private LocalDate date;

    @NotNull(message = "예약 시간을 입력해주세요.")
    private Time time;
}
