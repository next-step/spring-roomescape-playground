package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public class Room {
	Long id;
	String name;
	LocalDate date;
	LocalTime time;

	public void setId(Long id) {
		this.id = id;
	}

	public Room(String name, LocalDate date, LocalTime time) {
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
