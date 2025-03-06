package roomescape.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import roomescape.domain.reservation.Reservation;

public record ReservationResponseDto(
        Long id,
        String name,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate date,
        TimeResponse time
) {

        public static ReservationResponseDto toDto(Reservation reservation) {
                return new ReservationResponseDto(reservation.getId(), reservation.getName(),
                        reservation.reservedDateValue(), new TimeResponse(reservation.getTimeId(), reservation.getTime().getAvailableTime()));
        }
}
