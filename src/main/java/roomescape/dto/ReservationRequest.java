package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ReservationRequest {
    
    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotBlank(message = "날짜는 필수 입력값입니다.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜는 yyyy-MM-dd 형식이어야 합니다. (예: 2024-01-01)")
    private String date;

    @NotBlank(message = "시간은 필수 입력값입니다.")
    @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "시간은 HH:mm 형식이어야 합니다. (예: 15:40)")
    private String time;
}
