package roomescape.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import roomescape.dto.TimeDto;
import roomescape.service.TimeService;

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

    @ResponseBody
    @GetMapping("/times")
    public ResponseEntity<List<TimeDto>> times() {
        return ResponseEntity.ok(timeService.getAllTimes());
    }

    @ResponseBody
    @PostMapping("/times")
    public ResponseEntity<TimeDto> addTime(@RequestBody TimeDto request) throws URISyntaxException {
        var response = timeService.addTime(request);
        return ResponseEntity.created(new URI("/times/" + response.id())).body(response);
    }

    @ResponseBody
    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
