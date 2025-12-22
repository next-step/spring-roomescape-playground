package roomescape.web_layer.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service_layer.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TimeController {
    private final TimeService timeService;

    @GetMapping("/times")
    public List<TimeResponse> readTimes() {
        return timeService.getTime();
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> createTimes(@Valid @RequestBody TimeRequest request) {
        TimeResponse responses = timeService.registerTime(request);
        return ResponseEntity.created(URI.create("/times/" + responses.id())).body(responses);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTimes(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
