package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeDTO;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/time")
    public String getTimePage(Model model) {
        List<TimeDTO> times = timeService.getAllTimes();
        model.addAttribute("times", times);
        return "time";
    }

    @GetMapping("/times")
    @ResponseBody
    public List<TimeDTO> getTimes() {
        return timeService.getAllTimes();
    }

    @PostMapping("/times")
    public ResponseEntity<TimeDTO> addTime(@RequestBody TimeDTO timeDTO) {
        TimeDTO savedDTO = timeService.addTime(timeDTO);
        return ResponseEntity.created(URI.create("/times/" + savedDTO.id())).body(savedDTO);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
