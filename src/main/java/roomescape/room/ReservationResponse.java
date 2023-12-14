package roomescape.room;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

public class ReservationResponse {

	public record Create(Long id) {

		public static Create fromEntity(Long id) {
			return new Create(id);
		}
	}

	public record Read
			(Long id, String name, @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
			 Long time
			) {

		public static Read fromEntity(Reservation reservation) {
			return new Read(reservation.getId(), reservation.getName(), reservation.getDate(),
					reservation.getTime().getId());
		}

		public static List<Read> fromEntity(List<Reservation> reservations) {
			return reservations.stream().map(Read::fromEntity).toList();
		}
	}
}
