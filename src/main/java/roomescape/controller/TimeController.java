package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Time;
import roomescape.dto.TimeCreateCommand;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.dto.TimesResponse;
import roomescape.service.TimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<TimeResponse> createTime(
            @Valid @RequestBody TimeRequest request
    ) {
        TimeCreateCommand command = new TimeCreateCommand(request.time());

        Time time = timeService.createTime(command);
        TimeResponse response = TimeResponse.from(time);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<TimesResponse> getTimes() {
        List<TimeResponse> timeResponses = timeService.findAll().stream()
                .map(TimeResponse::from)
                .toList();
        TimesResponse response = TimesResponse.from(timeResponses);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(
            @PathVariable Long id
    ) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
