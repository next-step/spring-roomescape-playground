package roomescape.model;

import roomescape.dto.ReservationDto;

public record Reservation(long id, String name, String date, Time time) {
}
