package roomescape.time.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.time.domain.Time;
import roomescape.time.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    @GetMapping
    public ResponseEntity<List<Time>> read() {
        return ResponseEntity.status(HttpStatus.OK).body(timeService.getAllTimes());
    }

    @PostMapping
    public ResponseEntity<Time> create(@RequestBody Time request) {

        Time Time = timeService.createTime(request.getTime());

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/times/" + Time.getId()))
                .body(Time);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(TimeService.NotFoundTimeException.class)
    public ResponseEntity<String> handleException(TimeService.NotFoundTimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
