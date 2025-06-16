package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.RequestTime;
import roomescape.model.Time;
import roomescape.service.TimeService;

@Controller
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/time")
    public String time() {
        return "time";
    }

    @GetMapping("/times")
    @ResponseBody
    public ResponseEntity<List<Time>> timeList() {
        return ResponseEntity.ok().body(timeService.getTimeList());
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<Time> addTime(@RequestBody RequestTime requestTime) {
        Time newTime = timeService.addTime(requestTime);

        return ResponseEntity.created(URI.create("/times/" + newTime.getId())).body(newTime);
    }

    @DeleteMapping("/times/{id}")
    @ResponseBody
    public ResponseEntity<Void> cancelTime(@PathVariable Long id) {
        timeService.cancelTime(id);

        return ResponseEntity.noContent().build();
    }

}
