package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.time.request.TimeRequest;
import roomescape.dto.time.response.TimeResponse;
import roomescape.service.TimeService;

@RestController
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping("/times")
    @ResponseStatus(HttpStatus.CREATED)
    public TimeResponse createTime(@RequestBody TimeRequest request) {
        return timeService.createTime(request);
    }

    @GetMapping("/times")
    public List<TimeResponse> showTimes() {
        return timeService.findTimes();
    }

    @DeleteMapping("/times/{timeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTime(@PathVariable Long timeId) {
        timeService.deleteTime(timeId);
    }
}
