package roomescape.model;

import roomescape.dto.ReservationDto;

public record Reservation(long id, String name, String date, String time) {
    public Reservation(long id, ReservationDto reservationDto) {
        this(id, reservationDto.name(), reservationDto.date(), reservationDto.time());
    }

    public Reservation copy() {
        return new Reservation(this.id, this.name, this.date, this.time);
    }
}
