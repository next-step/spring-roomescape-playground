package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.entity.Time;
import roomescape.service.TimeService;

@Controller
public class TimeController {

  TimeService timeService;

  @Autowired
  TimeController(TimeService timeService) {
    this.timeService = timeService;
  }

  @GetMapping("/times")
  @ResponseBody
  public ResponseEntity<List<Time>> getAllTimes() {
    List<Time> body = timeService.searchAllTimes();
    return ResponseEntity.ok(body);
  }

  @PostMapping("/times")
  @ResponseBody
  public ResponseEntity<Time> createTime(@RequestBody @Valid Time request) {
    Time response = timeService.setTime(request);
    URI location = URI.create("/times/" + response.getId());

    return ResponseEntity.created(location).body(response);
  }

  @DeleteMapping("/times/{timeId}")
  @ResponseBody
  public ResponseEntity<Void> deleteTime(@PathVariable Long timeId) {
    timeService.removeTime(timeId);

    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler({NoSuchElementException.class, MethodArgumentNotValidException.class})
  public ResponseEntity<Void> handleException(Exception e) {
    return ResponseEntity.badRequest().build();
  }
}
