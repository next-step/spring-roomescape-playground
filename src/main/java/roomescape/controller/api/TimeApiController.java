package roomescape.controller.api;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeService;

@RestController
@RequestMapping("times")
@RequiredArgsConstructor
public class TimeApiController {

    private final TimeService timeService;

    @GetMapping
    public List<TimeResponse> times() {
        return timeService.times();
    }

    @PostMapping
    public ResponseEntity<TimeResponse> addTime(@Valid @RequestBody TimeRequest request) {
        TimeResponse time = timeService.addTime(request);
        return ResponseEntity.created(URI.create("/times/" + time.getId())).body(time);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
