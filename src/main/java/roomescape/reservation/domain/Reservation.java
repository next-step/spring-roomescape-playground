package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record Reservation(
		ReservationId id,
		String name,
		LocalDate date,
		LocalTime time
) {
	public static Reservation toEntity(Reservation reservation, ReservationId id) {
		return new Reservation(id, reservation.name, reservation.date, reservation.time);
	}
	
	public long getId() {
		return id.id();
	}
	
	public String getDate() {
		return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public String getTime() {
		return time.format(DateTimeFormatter.ofPattern("HH:mm"));
	}
}
