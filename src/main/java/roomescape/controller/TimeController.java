package roomescape.controller;

import jakarta.validation.Valid;
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
import roomescape.dto.time.TimeRequestDTO;
import roomescape.dto.time.TimeResponseDTO.AddTimeResponse;
import roomescape.dto.time.TimeResponseDTO.QueryTimeResponse;
import roomescape.service.TimeService;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class TimeController {
	private final TimeService timeService;

	@GetMapping
	public ResponseEntity<List<QueryTimeResponse>> getAllTimes() {
		return ResponseEntity.ok(timeService.getAllTimes());
	}

	@PostMapping
	public ResponseEntity<AddTimeResponse> addTime(@Valid @RequestBody TimeRequestDTO.AddTimeRequest addTimeRequest) {
		AddTimeResponse response = timeService.addTime(addTimeRequest.time());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
		timeService.deleteTime(id);
		return ResponseEntity.noContent().build();
	}
}