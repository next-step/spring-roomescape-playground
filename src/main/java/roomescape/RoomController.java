package roomescape;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoomController {

	private final RoomRepository roomRepository = new RoomRepository();

	@GetMapping("/reservation")
	public String getReservationPage() {
		return "reservation";
	}

	@PostMapping("/reservations")
	public ResponseEntity<RoomResponseDTO.Create> createRoom(@Valid @RequestBody RoomRequestDTO.Create request) {
		Long savedId = roomRepository.save(request.toEntity());
		return ResponseEntity.created(URI.create("/reservations/" + savedId))
				.body(RoomResponseDTO.Create.toDTO(savedId));
	}

	@GetMapping("/reservations/{id}")
	@ResponseBody
	public ResponseEntity<RoomResponseDTO.Read> getRoom(@PathVariable Long id) {
		Room room = roomRepository.findById(id);
		return ResponseEntity.ok().body(RoomResponseDTO.Read.toDTO(room));
	}

	@DeleteMapping("/reservations/{id}")
	@ResponseBody
	public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
		roomRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/reservations")
	@ResponseBody
	public List<RoomResponseDTO.Read> getList() {
		return RoomResponseDTO.Read.toDTO(roomRepository.findAll());
	}

	private class RoomRequestDTO {

		private record Create(@NotBlank(message = "이름은 필수 입력값입니다.") String name,
		                      @NotNull(message = "날짜는 필수 입력값입니다.") LocalDate date,
		                      @NotNull(message = "시간은 필수 입력값입니다.") LocalTime time) {

			public Room toEntity() {
				return new Room(name, date, time);
			}
		}
	}

	private class RoomResponseDTO {

		private record Create(Long id) {

			public static Create toDTO(Long id) {
				return new Create(id);
			}
		}

		private record Read
				(Long id, String name, @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
				 @JsonFormat(pattern = "HH:mm") LocalTime time
				) {

			public static Read toDTO(Room room) {
				return new Read(room.getId(), room.getName(), room.getDate(), room.getTime());
			}

			public static List<Read> toDTO(List<Room> rooms) {
				return rooms.stream().map(Read::toDTO).toList();
			}
		}
	}

}
