package roomescape.dto.time;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeRequestDTO {
	@NotEmpty
	public record AddTimeRequest(Long id, String time) {
	}
}
