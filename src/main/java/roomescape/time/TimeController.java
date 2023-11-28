package roomescape.time;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.time.TimeResponse.Create;
import roomescape.time.TimeResponse.Read;

@Controller
public class TimeController {

	private final TimeService timeService;

	public TimeController(TimeService timeService) {
		this.timeService = timeService;
	}

	@GetMapping("/time")
	public String getTimePage() {
		return "time";
	}

	@PostMapping("/times")
	@ResponseBody
	public ResponseEntity<TimeResponse.Create> createTime(@RequestBody TimeRequest.Create request) {
		Create response = timeService.create(request);
		return ResponseEntity.created(URI.create("/times/" + response.id()))
				.body(response);
	}

	@GetMapping("/times")
	@ResponseBody
	public ResponseEntity<List<TimeResponse.Read>> getList() {
		List<Read> response = timeService.getTimes();
		return ResponseEntity.ok()
				.body(response);
	}

	@GetMapping("/times/{id}")
	@ResponseBody
	public ResponseEntity<TimeResponse.Read> getTime(@PathVariable Long id) {
		Read response = timeService.getTimeById(id);
		return ResponseEntity.ok()
				.body(response);
	}

	@DeleteMapping("/times/{id}")
	@ResponseBody
	public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
		timeService.deleteTimeById(id);
		return ResponseEntity.noContent().build();
	}
}
