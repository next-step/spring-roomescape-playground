package roomescape.Controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeCreateForm;
import roomescape.dto.TimeResponseForm;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Controller
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/time")
    public String time(){
        return "time";
    }

    @GetMapping("/times")
    @ResponseBody
    public ResponseEntity<List<TimeResponseForm>> getTimes() {
        List<TimeResponseForm> times = timeService.getAllTimes();
        return ResponseEntity.ok().body(times);
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<TimeResponseForm> createTime(@Valid @RequestBody TimeCreateForm form, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new TimeResponseForm());
        }

        TimeResponseForm newTime = timeService.createTime(form);

        return ResponseEntity.created(URI.create("/Times/" + newTime.getId()))
                .body(newTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);

        return ResponseEntity.noContent().build();
    }

}
