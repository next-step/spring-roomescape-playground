package roomescape.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ReservationRequest {

    @NotBlank(message = "이름은 공백이 아니어야 합니다.")
    private String name;
    @NotEmpty(message = "날짜는 공백이 아니어야 합니다.")
    private String date;
    @NotNull(message = "시간은 공백이 아니어야 합니다.")
    private String time;

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
