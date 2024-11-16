package roomescape.Controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.Domain.Time;
import roomescape.Exception.NotFoundTimeException;
import roomescape.Service.TimeService;

@RestController
public class TimeController {

  private final TimeService timeService;

  public TimeController(TimeService timeService) {
    this.timeService = timeService;
  }

  @PostMapping("/times")
  public ResponseEntity<Time> create(@RequestBody Time time) {

    if (time.getTime() == null) {
      throw new NotFoundTimeException("Time not found");
    }

    // 시간 포맷 유효성 검사
    if (!time.getTime().matches("^\\d{2}:\\d{2}$")) {
      throw new NotFoundTimeException("Invalid time format");
    }

    Long id = timeService.addTimeByKey(time);

    Time savedTime = timeService.getTimeByID(id);

    return ResponseEntity.created(URI.create("/times/" + savedTime.getId()))
        .body(savedTime);
  }

  @GetMapping("/times")
  public ResponseEntity<List<Time>> read() {
    List<Time> times = timeService.getAllTimes();

    return ResponseEntity.ok().body(times);
  }

  @DeleteMapping("/times/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {

    List<Time> times = timeService.getTimesByID(id);

    if (times.isEmpty()) {
      throw new NotFoundTimeException("Time not found");
    }

    timeService.deleteTimeByID(id);

    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(NotFoundTimeException.class)
  public ResponseEntity<Void> handleException(NotFoundTimeException e) {
    return ResponseEntity.badRequest().build();
  }
}
