package roomescape.controller;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Time;
import roomescape.dto.TimeRequestDto;
import roomescape.service.TimeService;

@Controller
@RequiredArgsConstructor
public class TimeController {

  private final TimeService timeService;

  @GetMapping("/time")
  public String time() {
    return "time";
  }

  @GetMapping("/times")
  public ResponseEntity<List<Time>> getTimes() {
    List<Time> times = timeService.getTimes();
    return ResponseEntity.ok(times);
  }

  @PostMapping("/times")
  public ResponseEntity<Time> times(@RequestBody TimeRequestDto timeRequestDto) {
    Time time = timeService.addTime(timeRequestDto);
    return ResponseEntity
        .created(URI.create("/times/" + time.getId()))
        .body(time);
  }

  @DeleteMapping("/times/{id}")
  public ResponseEntity<Time> deleteTime(@PathVariable Long id) {
    timeService.removeTime(id);
    return ResponseEntity.noContent().build();
  }
}
