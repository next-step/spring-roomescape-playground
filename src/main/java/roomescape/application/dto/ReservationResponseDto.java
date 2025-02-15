package roomescape.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.Reservation;

public record ReservationResponseDto(
        Long id,
        String name,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate date,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        LocalTime time
) {

        public static ReservationResponseDto toDto(Reservation reservation) {
                return new ReservationResponseDto(reservation.getId(), reservation.getName(),
                        reservation.reservedDateValue(), reservation.reservedTimeValue());
        }
}
