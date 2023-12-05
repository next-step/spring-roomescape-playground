package roomescape.domain;

import lombok.Getter;

@Getter
public final class Reservation {
    private final Long id;
    private final String name;
    private final String date;
    private final Time time;

    public Reservation(Long id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation of(Long id, String name, String date, Time time) {
        return new Reservation(id, name, date, time);
    }

    public static Reservation createWithId(Reservation reservation, long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }


}
