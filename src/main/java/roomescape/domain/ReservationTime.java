package roomescape.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class ReservationTime {
    private final Long id;
    @NotNull
    private final LocalTime time;
}
