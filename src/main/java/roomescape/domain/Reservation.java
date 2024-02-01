package roomescape.domain;

import roomescape.dto.ReservationDto;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation(String name, String date, String time) {
        this(0L, name, date, time);
    }

    public Reservation(Long id, String name, String date, String time) {
        validateParams(date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateParams(String date, String time) {
        if (date.isBlank() || time.isBlank()) {
            throw new NotFoundReservationException();
        }
    }

    public ReservationDto toDTO() {
        return new ReservationDto(this.id, this.name, this.date, this.time);
    }

    public Reservation toEntity(Long id, Reservation reservation) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }
}