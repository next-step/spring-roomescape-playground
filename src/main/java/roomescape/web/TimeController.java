package roomescape.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.service.TimeService;
import roomescape.web.dto.TimeDto;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final TimeService timeService;

    @Autowired
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<TimeDto> create(@RequestBody Map<String, String> params) throws URISyntaxException
    {
        String time = params.get("time");

        TimeDto createTime = timeService.createTime(time);

        URI location = new URI("/times/"+ createTime.getId());
        return ResponseEntity.created(location).body(createTime);
    }

    @GetMapping
    public ResponseEntity<List<TimeDto>> getAllTimes() {
        List<TimeDto> times = timeService.getAllTime();
        return ResponseEntity.ok(times);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTimeById(id);
        return ResponseEntity.noContent().build();
    }
}
