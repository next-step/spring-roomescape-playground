package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoomeController {
	private final RoomRepository roomRepository = new RoomRepository();

	@GetMapping("/reservation")
	public String getReservationPage() {
		return "reservation";
	}

	@PostMapping("/reservations")
	public ResponseEntity<RoomResponseDTO.Create> createRoom(@RequestBody Room room) {
		room.validates();
		Long savedId = roomRepository.save(room);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.header("Location", "/reservations/" + savedId)
			.body(RoomResponseDTO.Create.toDTO(savedId));
	}

	@GetMapping("/reservations/{id}")
	@ResponseBody
	public ResponseEntity<RoomResponseDTO.Read> getRoom(@PathVariable Long id) {
		Room room = roomRepository.findById(id);
		return ResponseEntity
			.ok()
			.body(RoomResponseDTO.Read.toDTO(room));
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

	private static class RoomResponseDTO {
		private static class Create {
			private Long id;

			private Create(Long id) {
				this.id = id;
			}

			public static Create toDTO(Long id) {
				return new Create(id);
			}

			public Long getId() {
				return id;
			}
		}

		private static class Read {

			private Long id;
			private String name;
			private LocalDate date;
			private LocalTime time;

			private Read(Long id, String name, LocalDate date, LocalTime time) {
				this.id = id;
				this.name = name;
				this.date = date;
				this.time = time;
			}

			public static Read toDTO(Room room) {
				return new Read(room.getId(), room.getName(), room.getDate(), room.getTime());
			}

			public static List<Read> toDTO(List<Room> rooms) {
				return rooms.stream().map(Read::toDTO).toList();
			}

			public Long getId() {
				return id;
			}

			public String getName() {
				return name;
			}

			public String getDate() {
				return date.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			}

			public String getTime() {
				return time.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
			}
		}
	}

}
