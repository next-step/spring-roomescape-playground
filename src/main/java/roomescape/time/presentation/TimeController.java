package roomescape.time.presentation;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import roomescape.time.application.TimeService;
import roomescape.time.presentation.dto.request.TimeSaveRequest;
import roomescape.time.presentation.dto.response.TimeResponse;

@RequestMapping("/times")
@RestController
public class TimeController {

	private final TimeService timeService;

	public TimeController(TimeService timeService) {
		this.timeService = timeService;
	}

	@GetMapping
	public List<TimeResponse> getTimes() {
		return timeService.getTimes();
	}

	@PostMapping
	public ResponseEntity<TimeResponse> saveTime(@RequestBody TimeSaveRequest request) {
		TimeResponse response = timeService.saveTime(request);
		URI location = URI.create("/times/" + response.id());
		return ResponseEntity.created(location).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
		timeService.deleteTime(id);
		return ResponseEntity.noContent().build();
	}
}
