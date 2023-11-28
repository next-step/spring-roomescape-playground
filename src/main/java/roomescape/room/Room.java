package roomescape.room;

import java.time.LocalDate;
import java.time.LocalTime;

public class Room {
	private final Long id;
	private final String name;
	private final LocalDate date;
	private final LocalTime time;

	public Room(String name, LocalDate date, LocalTime time) {
		this.id = null;
		this.name = name;
		this.date = date;
		this.time = time;
	}

	public Room(Long id, String name, LocalDate date, LocalTime time) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.time = time;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getDate() {
		return date;
	}

	public LocalTime getTime() {
		return time;
	}

}
