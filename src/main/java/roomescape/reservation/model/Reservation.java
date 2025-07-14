package roomescape.reservation.model;

import java.time.LocalDate;
import roomescape.time.model.Time;

public record Reservation(Long id, String name, LocalDate date, Time time) {

}
