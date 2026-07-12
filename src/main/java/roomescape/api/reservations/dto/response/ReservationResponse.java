package roomescape.api.reservations.dto.response;

public record ReservationResponse(
        long id,
        String name,
        String date,
        String time
) {

    public static ReservationResponse of(final long id, final String name, final String date, final String time) {
        return new ReservationResponse(id, name, date, time);
    }
}
