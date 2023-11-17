package roomescape.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.model.Reservation;

public record CreateReservationRequest(
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {

    public Reservation toEntity() {
        return Reservation.builder()
                .date(date)
                .name(name)
                .time(time)
                .build();
    }
}
