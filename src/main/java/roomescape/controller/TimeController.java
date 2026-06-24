package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.TimeDto;
import roomescape.model.Time;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(TimeController.TIME_API_ENDPOINT_ROOT)
public class TimeController {

    public final static String TIME_API_ENDPOINT_ROOT = "/times";
    private final TimeService timeService;

    @Autowired
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Time> getAllTimes() {
        return this.timeService.getTimeList();
    }

    @PostMapping
    public ResponseEntity<Time> createTime(@RequestBody @Valid TimeDto timeDto) {
        Time newTime = this.timeService.add(timeDto);

        return ResponseEntity
                .created(URI.create(TIME_API_ENDPOINT_ROOT + "/" + newTime.id()))
                .body(newTime);
    }

    @DeleteMapping("/{deletingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTime(@PathVariable Long deletingId) {
        this.timeService.deleteTimeById(deletingId);
    }
}
