package roomescape.reservation;

import roomescape.exception.BadRequestException;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(Long id, String name, LocalDate date, LocalTime time) {
    public static Reservation of(Reservation reservation, Long id) {
        return new Reservation (id, reservation.name, reservation.date, reservation.time);
    }

    public void validateRequiredFields() {
        if (this.name() == null || this.name().isBlank() ||
                this.date() == null || this.time() == null) {
            throw new BadRequestException("예약 요청에 누락된 값이 있습니다.");
        }
    }
}
