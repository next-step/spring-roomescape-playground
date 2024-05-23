package roomescape.domain.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import roomescape.domain.Model.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class RequestDto {
    private String name;
    private String date;
    private String time;

    public Reservation toReservation() {
        LocalDateTime reservationDateTime = LocalDateTime.of(
                LocalDate.parse(date),
                LocalTime.parse(time)
        );
        return new Reservation(name, reservationDateTime);
    }
}