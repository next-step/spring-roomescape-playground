package roomescape.dto.response;

public class ReservationResponse {

	private long id;
	private String name;
	private String date;
	private String time;

	public ReservationResponse(long id, String name, String date, String time) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.time = time;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}
}
