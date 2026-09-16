package roomescape.controller.dto;


import java.time.LocalDate;


public record ReservationRequestDto(
        String name,
        LocalDate date,
        Long time
) {
    public ReservationRequestDto {
        if (name == null || name.isBlank() || date == null || time == null) {
            throw new IllegalArgumentException("필수 인자가 누락되었습니다.");
        }
    }
}


