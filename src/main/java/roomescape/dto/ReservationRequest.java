package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Getter
public class ReservationRequest {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "날짜는 필수입니다.")
    private String date;

    @NotNull(message = "시간 ID는 필수입니다.")
    private Long timeId;

    public LocalDate getParsedDate() {
        return LocalDate.parse(date);
    }

    public Reservation toEntity(Time time) {
        return new Reservation(null, name, LocalDate.parse(date), time);
    }

}
