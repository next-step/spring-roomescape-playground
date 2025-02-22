package roomescape.domain.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.global.exception.RoomescapeBadRequestException;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {

    public ReservationRequest {
        if (name == null || name.isBlank() || date == null || time == null) {
            throw new RoomescapeBadRequestException("잘못된 예약 데이터입니다: name/date/time이 null이거나 빈 값입니다.");
        }
    }

    public Reservation newReservation() {
        return Reservation.newWithoutId(this.name(), this.date(), this.time());
    }
}
