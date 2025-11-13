package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(int id, String name, LocalDate date, LocalTime time) { }
