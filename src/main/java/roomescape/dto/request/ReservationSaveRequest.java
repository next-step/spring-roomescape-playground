package roomescape.dto.request;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ReservationSaveRequest {

	private String name;
	private String date;
	private String time;

	public ReservationSaveRequest() {
		validate();
	}

	private void validate() {
		if (name == null || name.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be empty");
		}
		if (date == null || date.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date cannot be empty");
		}
		if (time == null || time.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time cannot be empty");
		}
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
