package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeDto;
import roomescape.service.TimeService;

import java.util.List;

@Controller
public class TimeController {

    private TimeService timeService;

    public TimeController(TimeService timeService){
        this.timeService = timeService;
    }




    @GetMapping("/time")
    public String time() {

        return "time";
    }

    @GetMapping("/times")
    @ResponseBody
    public ResponseEntity<List<TimeDto>> getTimes() {

        return ResponseEntity.ok(timeService.getTimes());
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<TimeDto> postTime(@RequestBody @Valid TimeDto timeDto) {

        return timeService.postime(timeDto);
    }

    @DeleteMapping("/times/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {

        timeService.deleteTime(id);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}

