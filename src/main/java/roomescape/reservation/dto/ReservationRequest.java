package roomescape.reservation.dto;

import roomescape.exception.BadRequestException;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {

    public ReservationRequest {
        validateRequiredFields();
    }

    private void validateRequiredFields() {
        if (this.name() == null || this.name().isBlank() ||
                this.date() == null || this.time() == null) {
            throw new BadRequestException("예약 요청에 누락된 값이 있습니다.");
        }
    }
}
