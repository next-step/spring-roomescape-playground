package roomescape.reservation.request;

import jakarta.annotation.Nonnull;
import java.time.LocalDate;

public record ReservationRequest(
    @Nonnull LocalDate date,
    @Nonnull String name,
    @Nonnull Long time
) {

}
