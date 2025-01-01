package roomescape.reservationManage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddReservationRequest {
    @NotBlank(message = "이름은 필수 값입니다.")
    final private String name;
    @NotBlank(message = "날짜는 필수 값입니다.")
    final private String date;
    @NotNull(message = "timeId는 필수 값입니다.")
    final private Long time;

    public AddReservationRequest(String name, String date, Long time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getTime() {
        return time;
    }
}
