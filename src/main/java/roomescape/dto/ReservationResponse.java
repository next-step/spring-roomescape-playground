package roomescape.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReservationResponse {

    private Long id;

    private String name;

    private LocalDate date;

    private Time time;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
    }
}
