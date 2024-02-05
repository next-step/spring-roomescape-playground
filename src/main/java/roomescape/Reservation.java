package roomescape;

import lombok.AllArgsConstructor;
import roomescape.DTO.ReservationDTO;
import roomescape.value.Date;
import roomescape.value.ID;
import roomescape.value.Name;
import roomescape.value.Time;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
public class Reservation {


    private ID id;
    private Name name;
    private Date date;
    private Time time;

    public ReservationDTO toDTO() {
        return new ReservationDTO(this.id.getID(), this.name.getName(), this.date.getDate(), this.time.getTime());
    }

    public Long getID() {
        return id.getID();
    }

    public String getName() {
        return name.getName();
    }

    public LocalDate getDate() {
        return date.getDate();
    }

    public LocalTime getTime() {
        return time.getTime();
    }
}
