package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReservationRequestDto {
    @NotBlank(message = "예약자 이름을 입력해 주세요.")
    private String name;

    @NotBlank(message = "예약 날짜를 입력해 주세요.")
    private String date;

    @NotNull(message = "예약 시간을 선택해 주세요.")
    private Long time;

    public ReservationRequestDto(String name, String date, Long time) {
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


