package roomescape.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class Reservation {
    private final Long id;
    @NotBlank
    private final String name;
    @NotNull
    private final LocalDate date;
    @NotNull
    private final Time time;

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }
}
