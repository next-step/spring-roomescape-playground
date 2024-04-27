package roomescape.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.Domain.Time;
import roomescape.Service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/times")
public class TimeController {
  @Autowired
  private TimeService timeService;
  @GetMapping
  public List<Time> getAllTimes() {
    return timeService.getAllTimes();
  }

  @PostMapping
  public ResponseEntity<Time> newTime(@RequestBody Time time) {
    Time newTime = timeService.newTime(time);
    return ResponseEntity.created(URI.create("/times/" + newTime.getId())).body(newTime);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> deleteTimeById(@PathVariable Long id) {
    try {
      timeService.deleteTimeById(id);
    }
    catch (IncorrectResultSizeDataAccessException error) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.noContent().build();
  }
}
