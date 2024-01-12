package roomescape.domain.time.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.time.dto.TimeDTO;
import roomescape.domain.time.entity.Time;
import roomescape.domain.time.service.TimeService;

@Validated
@Controller
@RequiredArgsConstructor
public class TimeController {
    private final TimeService timeService;

    @GetMapping("/time")
    public String getTimePage() {
        return "time";
    }

    @PostMapping("/times")
    public ResponseEntity<Time> createTime(@Valid @RequestBody TimeDTO requestDto) {
        Time time = timeService.createTime(requestDto.time());
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/times/" + time.getId()))
                .body(time);
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimes() {
        List<Time> times = timeService.getTimes();
        return ResponseEntity.ok(times);
    }

    @DeleteMapping("/times/{timeId}")
    public ResponseEntity<Void> deleteTime(@PathVariable long timeId) {
        timeService.deleteTime(timeId);
        return ResponseEntity.noContent().build();
    }
}
