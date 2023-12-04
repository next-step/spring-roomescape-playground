package roomescape.domain;

public record Reservation(Long id, String name, String date, Long time) {

    public static Reservation of(Long id, String name, String date, Long time) {
        return new Reservation(id, name, date, time);
    }

    public static Reservation createWithId(Reservation reservation, long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

}
