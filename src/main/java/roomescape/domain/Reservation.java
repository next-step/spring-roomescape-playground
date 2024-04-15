package roomescape.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
    private final ReservationTime time;

}
