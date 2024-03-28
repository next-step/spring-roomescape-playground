package roomescape.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class ReservationDto {

    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation toEntity() {
        return new Reservation(null, name, date, time);
    }
}