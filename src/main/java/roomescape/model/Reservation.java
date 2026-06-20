package roomescape.model;

import java.time.LocalDate;

public record Reservation(Long id, String name, LocalDate date, Time time) {
}
