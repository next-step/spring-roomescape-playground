package roomescape.time.domain;

public class Time {

	Long id;
	String time;

	public Time(String time) {
		this.id = null;
		this.time = time;
	}

	public Time(Long id, String time) {
		this.id = id;
		this.time = time;
	}

	public Long getId() {
		return id;
	}

	public String getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "Time [id=" + id + ", time=" + time + "]";
	}
}
