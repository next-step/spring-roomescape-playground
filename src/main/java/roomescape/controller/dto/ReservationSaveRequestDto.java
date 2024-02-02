package roomescape.controller.dto;

import jakarta.validation.constraints.NotEmpty;

public record ReservationSaveRequestDto(String name,
                                        String date,
                                        @NotEmpty(message = "예약을 생성할 수 없습니다. 예약 시간이 존재하지 않습니다.") String time) {
    public long timeId() {
        try {
            return Long.parseLong(time);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("예약을 생성할 수 없습니다. 예약 시간 id가 올바르지 않습니다.");
        }
    }
}
