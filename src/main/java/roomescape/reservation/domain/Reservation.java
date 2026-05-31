package roomescape.reservation.domain;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import roomescape.time.domain.Time;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class Reservation {
    private final ReservationId id;
    private final String name;
    private final LocalDate date;
    private final Time time;

    public Reservation(
            @Nonnull ReservationId id,
            @Nonnull String name,
            @Nonnull LocalDate date,
            @Nonnull Time time
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public @NotNull ReservationId getId() {
        return id;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull LocalDate getDate() {
        return date;
    }

    public @NotNull Time getTime() {
        return time;
    }
}
