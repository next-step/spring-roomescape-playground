package roomescape.Domains.Reservation;

import org.springframework.lang.NonNull;

public record ReservationStoreRequestDto(
    String date,
    String time,
    String name
) {
}
