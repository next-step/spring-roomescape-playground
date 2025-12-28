package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import roomescape.dto.TimeCreateRequest;
import roomescape.dto.TimeCreateResponse;
import roomescape.model.Time;
import roomescape.service.TimeService;

@Controller
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    /*
     * View Mapping
     */

    @GetMapping("/time")
    public String time() {
        return "time";
    }

    /*
     * API Mapping
     */

    @ResponseBody
    @GetMapping("/times")
    public List<Time> getTimes() {
        return timeService.getTimes();
    }

    @ResponseBody
    @PostMapping("/times")
    public ResponseEntity<TimeCreateResponse> createTime(@RequestBody @Valid TimeCreateRequest request) {
        Time time = timeService.createTime(request);

        URI location = URI.create("/times/" + time.id());
        return ResponseEntity.created(location).body(TimeCreateResponse.from(time));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/times/{id}")
    public void deleteTime(@PathVariable int id) {
        timeService.deleteTime(id);
    }
}
