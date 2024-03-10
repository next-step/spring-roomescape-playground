package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.repository.domain.Time;
import roomescape.dto.TimeDTO;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {

    private final TimeService timeService;

    @Autowired
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/time")
    public String time() {
        return "time";
    }

    @GetMapping("/times")
    @ResponseBody
    public ResponseEntity<List<TimeDTO>> read() {
        List<TimeDTO> timeDTOS = timeService.getAllTimes();
        return ResponseEntity.ok().body(timeDTOS);
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<TimeDTO> create(@RequestBody Time time) {
        TimeDTO timeDTO = timeService.insertTime(time);
        return ResponseEntity.created(URI.create("/times/" + timeDTO.getId())).body(timeDTO);
    }

    @DeleteMapping("/times/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.deleteTimeById(id);
        return ResponseEntity.noContent().build();
    }
}
