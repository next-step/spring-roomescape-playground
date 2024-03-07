package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

public record ReservationResponseDto(Long id, String name, String date, Time time) {
    public static ReservationResponseDto from (Reservation reservation){
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
    }
}
