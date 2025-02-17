package roomescape.entity;

import roomescape.entity.Dto.ReservationOutDto;
import roomescape.entity.value.Date;
import roomescape.entity.value.Name;
import roomescape.entity.value.Time;

public class Reservation {

    private final Long id;
    private final Name name;
    private final Date date;
    private final Time time;

    public Reservation(Long id, String name, String date, Time time) {
        this.id = id;
        this.name = Name.of(name);
        this.date = Date.of(date);
        this.time = time;
    }

    public static Reservation of(ReservationOutDto reservationOutDto, Time time) {
        return new Reservation(reservationOutDto.getId(), reservationOutDto.getName(), reservationOutDto.getDate(), time);
    }

    private Reservation() {
        this.id = null;
        this.name = null;
        this.date = null;
        this.time = null;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public String getDate() {
        return date.getValue();
    }

    public Time getTime() {
        return time;
    }


}
