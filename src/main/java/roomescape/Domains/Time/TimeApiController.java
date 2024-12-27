package roomescape.Domains.Time;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeApiController {

  private final TimeService timeService;

  public TimeApiController(TimeService timeService) {
    this.timeService = timeService;
  }

  @GetMapping("/times")
  public ResponseEntity<List<Time>> index() {
    List<Time> times = timeService.list();

    return ResponseEntity.ok().body(times);
  }

  @PostMapping("/times")
  public ResponseEntity<Time> store(@RequestBody TimeStoreRequestDto requestDto) {
    Time time = timeService.create(requestDto);

    return ResponseEntity.created(URI.create("/times/1")).body(time);
  }

  @DeleteMapping("/times/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    timeService.deleteByIdOrElseThrowException(id);

    return ResponseEntity.noContent().build();
  }
}
