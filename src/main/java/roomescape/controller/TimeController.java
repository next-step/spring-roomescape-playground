package roomescape.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeDTO;
import roomescape.entity.Time;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {

    private TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }




    @GetMapping("/time")
    public String time() {
        return "time";
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> showAll() {

        return new ResponseEntity<>(timeService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeDTO> create(@RequestBody @Valid TimeDTO request) {
        int id = timeService.create(request);
        return ResponseEntity.created(URI.create("/times/" + id)).build();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<TimeDTO> delete(@PathVariable Long id) {
        timeService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
