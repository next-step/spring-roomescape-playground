package roomescape.dto.response;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class ReservationCreateResponse {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public static ReservationCreateResponse from(Reservation reservation) {
        return ReservationCreateResponse.builder()
                .id(reservation.getId())
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .build();
    }
}
