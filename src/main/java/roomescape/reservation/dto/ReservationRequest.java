package roomescape.reservation.dto;

import roomescape.exception.BadRequestException;

import java.time.LocalDate;

public record ReservationRequest(String name, LocalDate date, Long time) {

    public ReservationRequest {
        validateRequiredFields(name, date, time);
    }

    private void validateRequiredFields(String name, LocalDate date, Long time) {
        if (name == null || name.isBlank() || date == null || time == null) {
            throw new BadRequestException("예약 요청에 누락된 값이 있습니다.");
        }
    }
}
