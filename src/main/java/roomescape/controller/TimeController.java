package roomescape.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Time;
import roomescape.service.TimeService;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class TimeController {
	private final TimeService timeService;

	@GetMapping
	public ResponseEntity<List<Time>> getAllTimes() {
		List<Time> times = timeService.getAllTimes();
		return ResponseEntity.ok(times);
	}

	@PostMapping
	public ResponseEntity<Time> addTime(@RequestBody String time) {
		Time addedTime = timeService.addTime(time);
		return ResponseEntity.status(HttpStatus.CREATED).body(addedTime);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
		timeService.deleteTime(id);
		return ResponseEntity.noContent().build();
	}
}