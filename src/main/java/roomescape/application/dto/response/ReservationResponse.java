package roomescape.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import roomescape.domain.reservation.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate date,
        TimeResponse time
) {

        public static ReservationResponse toDto(Reservation reservation) {
                return new ReservationResponse(reservation.getId(), reservation.getName(),
                        reservation.reservedDateValue(), new TimeResponse(reservation.getTimeId(), reservation.getTime().getTime()));
        }
}
