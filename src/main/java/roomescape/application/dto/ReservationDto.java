package roomescape.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class ReservationDto {

    private final String name;
    private final LocalDate date;
    private final Long timeId;
}
