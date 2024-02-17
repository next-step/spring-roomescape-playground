package roomescape.domain;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Reservation {
	private Long id;
	@NotBlank
	private String name;
	@NotBlank
	private String date;
	@NotBlank
	private String time;
}
