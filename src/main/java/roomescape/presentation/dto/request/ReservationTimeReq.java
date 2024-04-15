package roomescape.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ReservationTimeReq {
    @NotNull(message = "예약 시간을 입력해주세요.")
    private LocalTime time;

    public ReservationTimeReq() {
    }
}
