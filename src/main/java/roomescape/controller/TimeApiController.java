package roomescape.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class TimeApiController {

    private final TimeService timeService;

    @PostMapping
    public ResponseEntity<TimeResponse> create(@RequestBody TimeRequest request) {
        TimeResponse response = timeService.create(request);
        return ResponseEntity.created(URI.create("/times/" + response.getId()))
                .body(response);
    }

    @GetMapping
    public List<TimeResponse> findAll() {
        return timeService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
