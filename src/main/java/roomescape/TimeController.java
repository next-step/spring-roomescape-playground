package roomescape;

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

@Controller
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/time")
    public String showTimePage() {
        return "time";
    }

    @PostMapping("/times")
    public ResponseEntity<Time> createTime(@RequestBody TimeRequest timeRequest) {
        Time time = timeService.createTime(timeRequest);

        return ResponseEntity
            .created(URI.create("/times/" + time.getId()))
            .body(time);
    }

    @GetMapping("/times")
    @ResponseBody
    public List<Time> readTimes() {
        return timeService.readTimes();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable long id) {
        timeService.deleteTime(id);

        return ResponseEntity.noContent().build();
    }

}
