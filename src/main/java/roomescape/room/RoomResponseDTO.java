package roomescape.room;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

public class RoomResponseDTO {

	public record Create(Long id) {

		public static Create toDTO(Long id) {
			return new Create(id);
		}
	}

	public record Read
			(Long id, String name, @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
			 Long time
			) {

		public static Read toDTO(Room room) {
			return new Read(room.getId(), room.getName(), room.getDate(), room.getTime().getId());
		}

		public static List<Read> toDTO(List<Room> rooms) {
			return rooms.stream().map(Read::toDTO).toList();
		}
	}
}
