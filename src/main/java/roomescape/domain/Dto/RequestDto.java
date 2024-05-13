package roomescape.domain.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import roomescape.domain.Model.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RequestDto {
    private String name;
    private String date;
    private String time;

    public Reservation toReservation() {
        Reservation reservation = new Reservation();
        reservation.setName(name);
        reservation.setDate(date);
        reservation.setTime(time);
        return reservation;
    }
}