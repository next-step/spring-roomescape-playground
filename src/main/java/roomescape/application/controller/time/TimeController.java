package roomescape.application.controller.time;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
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
import roomescape.domain.time.Time;

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
        List<Time> times = timeService.findAll();
        List<TimeResponse> timeResponses = times.stream()
                .map(it -> new TimeResponse(it.getId(), it.getTime()))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(timeResponses);
    }

    @DeleteMapping("/{timeId}")
    public ResponseEntity<Void> deleteTime(
            @PathVariable Long timeId
    ) {
        timeService.deleteTime(timeId);
        return ResponseEntity.noContent().build();
    }
}
