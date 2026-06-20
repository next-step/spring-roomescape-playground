package roomescape.reservation.dto;

import roomescape.reservation.model.Reservation;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ReservationRequest {
    public String name;
    public String date;
    public String time;

    public Reservation toEntity(){
        try {
            return new Reservation(
                    this.name,
                    LocalDate.parse(this.date)
            );
        } catch (DateTimeParseException e) {
            throw e;
        }
    }
}