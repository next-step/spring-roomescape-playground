package roomescape.domain.reservation.dto;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReservationCreateDTO {
    @NotBlank(message="이름은 공백이 될 수 없습니다.")
    String name;
    @NotNull(message = "날짜를 입력해주세요.")
    LocalDate date;
    @NotNull(message = "시간을 선택해주세요.")
    Long time;

    ReservationCreateDTO() {

    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getTime() {
        return time;
    }
}
