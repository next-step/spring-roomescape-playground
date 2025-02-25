package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.time.request.TimeCreateRequest;
import roomescape.dto.time.response.TimeCreateResponse;
import roomescape.service.TimeService;

@RestController
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping("/times")
    @ResponseStatus(HttpStatus.CREATED)
    public TimeCreateResponse createTime(@RequestBody TimeCreateRequest request) {
        return timeService.createTime(request);
    }
}
