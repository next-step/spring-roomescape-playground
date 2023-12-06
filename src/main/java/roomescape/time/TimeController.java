package roomescape.time;

import static org.springframework.http.HttpStatus.CREATED;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.dto.TimeRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/times")
public class TimeController {

  private final TimeService timeService;

  @GetMapping
  public ResponseEntity<List<Time>> getAllTimeInfo() {
    return ResponseEntity.ok(timeService.getTimeInfo());
  }

  @PostMapping
  public ResponseEntity<Time> addTime(@RequestBody TimeRequest request) {
    Time time = timeService.addTime(request.getTime());
    return ResponseEntity.status(CREATED)
        .location(URI.create("/times/" + time.getId())).body(time);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
    timeService.deleteTime(id);
    return ResponseEntity.noContent().build();
  }

}
