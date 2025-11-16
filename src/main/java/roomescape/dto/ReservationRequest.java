package roomescape.dto;


import jakarta.validation.constraints.NotBlank;

public record ReservationRequest(
        @NotBlank(message = "이름은 비어있을 수 없습니다.")
        String name,

        @NotBlank(message = "날짜는 비어있을 수 없습니다.")
        String date,

        @NotBlank(message = "시간은 비어있을 수 없습니다.")
        String time
) {
}
