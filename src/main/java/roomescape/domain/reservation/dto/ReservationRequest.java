package roomescape.domain.reservation.dto;

import java.time.LocalDate;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservationTime.domain.ReservationTime;
import roomescape.global.exception.RoomescapeBadRequestException;

public record ReservationRequest(String name, LocalDate date, long time) {

    public ReservationRequest {
        if (name == null || name.isBlank() || date == null) {
            throw new RoomescapeBadRequestException("잘못된 예약 데이터입니다.");
        }
    }

    public Reservation newReservation(final ReservationTime reservationTime) {
        return Reservation.newWithoutId(this.name(), this.date(), reservationTime);
    }
}
