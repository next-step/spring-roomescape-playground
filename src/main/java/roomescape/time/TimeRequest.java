package roomescape.time;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public class TimeRequest {

	public record Create(@NotNull LocalTime time) {

		public Time toEntity() {
			return new Time(time);
		}
	}
}
