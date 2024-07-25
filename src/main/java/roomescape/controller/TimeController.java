package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.TimeDto;
import roomescape.dto.TimeResDto;
import roomescape.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<TimeResDto> addTime(@RequestBody TimeDto timeDto) {
        TimeResDto timeResDto = timeService.addTime(timeDto);
        HttpHeaders headers = createHeader("/times/" + timeResDto.id());
        return new ResponseEntity<>(timeResDto, headers, HttpStatus.CREATED);
    }

    private HttpHeaders createHeader(String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", path);
        return headers;
    }

    @GetMapping
    public ResponseEntity<List<TimeResDto>> getAllTimes() {
        List<TimeResDto> times = timeService.getAllTimes();
        return new ResponseEntity<>(times, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable int id) {
        timeService.deleteTime(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
