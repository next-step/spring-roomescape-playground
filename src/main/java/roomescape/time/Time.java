package roomescape.time;

import java.time.LocalTime;

public class Time {
	private Long id;
	private LocalTime time;

	public Time(LocalTime time) {
		this.time = time;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public LocalTime getTime() {
		return time;
	}
}
