package roomescape.reservation.domain;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public final class Reservation {
    private final ReservationId id;
    private final String name;
    private final LocalDateTime time;

    public Reservation(
            @Nonnull ReservationId id,
            @Nonnull String name,
            @Nonnull LocalDateTime time
    ) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public @NotNull ReservationId getId() {
        return id;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull LocalDateTime getTime() {
        return time;
    }
}
