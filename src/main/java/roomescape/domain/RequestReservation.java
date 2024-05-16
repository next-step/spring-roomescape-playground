package roomescape.domain;

public record RequestReservation(
        String name,
        String date,
        String time
) {
    public Reservation toReservation() {
        return new Reservation(name, date, time);
    }
}
