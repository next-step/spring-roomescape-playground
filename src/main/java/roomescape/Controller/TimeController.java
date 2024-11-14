package roomescape.controller;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Time;
import roomescape.dto.request.TimeRequestDto;
import roomescape.dto.response.TimeResponseDto;
import roomescape.service.TimeService;

@RequiredArgsConstructor
@Controller
public class TimeController {

  private final TimeService timeService;

  @GetMapping("/time")
  public String time() {
    return "time";
  }

  @GetMapping("/times")
  public ResponseEntity<List<TimeResponseDto>> getTimes() {
    List<Time> times = timeService.findAll();
    return ResponseEntity.ok().body(TimeResponseDto.from(times));
  }

  @PostMapping("/times")
  public ResponseEntity<TimeResponseDto> creatTime(@RequestBody TimeRequestDto request) {

    Time time = request.toTime();
    timeService.create(time);

    String uri = "/times/" + time.getId();
    return ResponseEntity.created(URI.create(uri)).body(TimeResponseDto.from(time));
  }

  @DeleteMapping("/times/{id}")
  public ResponseEntity<TimeResponseDto> deleteTime(@PathVariable Long id) {
    timeService.delete(id);
    return ResponseEntity.noContent().build();
  }
}