package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(Integer id, String name, LocalDate date, LocalTime time) { }
