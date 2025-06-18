package roomescape.dto;

import jakarta.validation.constraints.NotBlank;

public class ReservationRequest {
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "날짜는 필수입니다.")
    private String date;

    @NotBlank(message = "시간은 필수입니다.")
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
