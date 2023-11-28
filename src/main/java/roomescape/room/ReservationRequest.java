package roomescape.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import roomescape.time.Time;

public class ReservationRequest {

	public record Create(@NotBlank(message = "이름은 필수 입력값입니다.") String name,
	                     @NotNull(message = "날짜는 필수 입력값입니다.") LocalDate date,
	                     @NotNull(message = "시간은 필수 입력값입니다.") Long time) {

		public Reservation toEntity() {
			Time timeObj = new Time(null);
			timeObj.setId(time);
			return new Reservation(name, date, timeObj);
		}
	}
}
