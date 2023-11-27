package roomescape.domain;

public record Reservation(Long id, String name, String date, String time) {

    public static Reservation of(long id, String name, String date, String time) {
        return new Reservation(id, name, date, time);
    }

    public static Reservation createWithId(Reservation reservation, long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

}
