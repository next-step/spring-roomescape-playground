package roomescape.reservation.dto;

import roomescape.exception.BadRequestException;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {

    public ReservationRequest {
        validateRequiredFields(name, date, time);
    }

    private void validateRequiredFields(String name, LocalDate date, LocalTime time) {
        if (name == null || name.isBlank() || date == null || time == null) {
            throw new BadRequestException("예약 요청에 누락된 값이 있습니다.");
        }
    }
}
