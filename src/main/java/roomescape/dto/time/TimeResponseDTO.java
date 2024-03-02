package roomescape.dto.time;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeResponseDTO {
	@NotEmpty
	public record QueryTimeResponse(Long id, String time) {
	}

	@NotEmpty
	public record AddTimeResponse(Long id, String time) {
	}
}
