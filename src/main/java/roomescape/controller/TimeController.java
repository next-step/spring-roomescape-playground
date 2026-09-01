package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Time;
import roomescape.dto.TimeCreateCommand;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeService;

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

}
