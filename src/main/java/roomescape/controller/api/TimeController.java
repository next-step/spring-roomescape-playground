package roomescape.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TimeController {
    private final TimeService timeService;


    @PostMapping("/times")
    public ResponseEntity<TimeResponse> createTimes(@Valid @RequestBody TimeRequest request) {
        Time newTime = timeService.registerTime(request.time());
        return ResponseEntity.created(URI.create("/times/" + newTime.getId())).
                body(TimeResponse.from(newTime));
    }


    @GetMapping("/times")
    public List<TimeResponse> readTimes() {
        List<Time> times = timeService.getTime();

        List<TimeResponse> responses = times.stream().
                map(TimeResponse::from).toList();

        return responses;
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTimes(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
