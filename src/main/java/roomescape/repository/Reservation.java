package roomescape.repository;

public record Reservation(Long id, String name, String date, String time) {

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }
}
