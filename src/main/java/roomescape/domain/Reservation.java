package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.controller.dto.RequestReservation;

public record Reservation(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {

    public static Reservation of(final Long id, final RequestReservation request) {
        return new Reservation(id, request.name(), request.date(), request.time());
    }
}
