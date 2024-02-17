package roomescape.web.dto;

import jdk.jfr.StackTrace;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;



@NoArgsConstructor
@Getter
@Setter
public class ReserveListResponseDto {
    private Long id;
    private String name;
    private String date;
    private String time;

    public ReserveListResponseDto(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
    }


    public static ReserveListResponseDto fromEntity(Reservation reservation) {
        return new ReserveListResponseDto(reservation);
    }

}
