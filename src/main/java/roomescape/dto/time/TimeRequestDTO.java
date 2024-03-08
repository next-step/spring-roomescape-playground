package roomescape.dto.time;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeRequestDTO {
	public record AddTimeRequest(Long id, String time) {
	}
}
