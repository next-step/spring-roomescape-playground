package roomescape.time;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class TimeResponse {

	public record Create(Long id, @JsonFormat(pattern = "HH:mm") LocalTime time) {

		public static Create fromEntity(Time time) {
			return new Create(time.getId(), time.getTime());
		}
	}

	public record Read(Long id, @JsonFormat(pattern = "HH:mm") LocalTime time) {

		public static Read fromEntity(Time time) {
			return new Read(time.getId(), time.getTime());
		}

		public static List<Read> fromEntity(List<Time> times) {
			return times.stream().map(Read::fromEntity).collect(Collectors.toList());
		}
	}
}
