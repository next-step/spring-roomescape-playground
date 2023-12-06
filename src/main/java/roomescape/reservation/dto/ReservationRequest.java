package roomescape.reservation.dto;

import lombok.Getter;
import roomescape.time.domain.Time;

import java.time.LocalDate;

@Getter
public class ReservationRequest {
    private String name;
    private LocalDate date;
    private Long time;

    public ReservationRequest(){
    }

    public ReservationRequest(String name, LocalDate date, Long time){
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
