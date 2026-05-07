package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ReservationRequest {
    @NotBlank(message = "이름은 비어 있을 수 없습니다.")
    @Size(max = 10, message = "이름은 10자 이하만 가능합니다.")
    private String name;

    @NotBlank(message = "날짜는 비어 있을 수 없습니다.")
    private String date;

    @NotBlank(message = "시간은 비어 있을 수 없습니다.")
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
