package roomescape.application.controller.time;

import jakarta.validation.Valid;
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
import roomescape.application.dto.request.CreateTimeRequest;
import roomescape.application.dto.response.TimeResponse;
import roomescape.application.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<TimeResponse> createTime(
            @RequestBody @Valid CreateTimeRequest request
    ) {
        Long savedId = timeService.saveTime(request);
        TimeResponse timeResponse = new TimeResponse(savedId, request.time());
        URI uri = URI.create("/times/" + savedId);
        return ResponseEntity.created(uri).body(timeResponse);
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> findAll() {
        List<TimeResponse> times = timeService.findAll();
        return ResponseEntity.ok(times);
    }

    @DeleteMapping("/{timeId}")
    public ResponseEntity<Void> deleteTime(
            @PathVariable Long timeId
    ) {
        timeService.deleteTime(timeId);
        return ResponseEntity.noContent().build();
    }
}
