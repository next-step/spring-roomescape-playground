package roomescape.domain.reservation.entity;

import roomescape.domain.time.entity.Time;

public record Reservation(Long id, String name, String date, Time time) {

}
