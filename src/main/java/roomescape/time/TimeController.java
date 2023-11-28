package roomescape.time;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TimeController {

	private final TimeRepository timeRepository;

	public TimeController(TimeRepository timeRepository) {
		this.timeRepository = timeRepository;
	}

	@GetMapping("/time")
	public String getTimePage() {
		return "time";
	}

	@PostMapping("/times")
	@ResponseBody
	public ResponseEntity<TimeResponse.Create> createTime(@RequestBody TimeRequest.Create request) {
		Time savedTime = timeRepository.save(request.toEntity());
		return ResponseEntity.created(URI.create("/times/" + savedTime.getId()))
				.body(TimeResponse.Create.toDTO(savedTime));
	}

	@GetMapping("/times")
	@ResponseBody
	public ResponseEntity<List<TimeResponse.Read>> getList() {
		return ResponseEntity.ok()
				.body(TimeResponse.Read.toDTO(timeRepository.findAll()));
	}

	@DeleteMapping("/times/{id}")
	@ResponseBody
	public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
		timeRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	private class TimeRequest {
		record Create(@NotNull LocalTime time) {
			public Time toEntity() {
				return new Time(time);
			}
		}
	}

	private class TimeResponse {
		record Create(Long id, @JsonFormat(pattern = "HH:mm") LocalTime time) {
			public static Create toDTO(Time time) {
				return new Create(time.getId(), time.getTime());
			}
		}

		record Read(Long id, @JsonFormat(pattern = "HH:mm") LocalTime time) {
			public static Read toDTO(Time time) {
				return new Read(time.getId(), time.getTime());
			}

			public static List<Read> toDTO(List<Time> times) {
				return times.stream().map(Read::toDTO).collect(Collectors.toList());
			}
		}
	}
}
