package roomescape.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Time;
import roomescape.service.TimeService;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class TimeController {

    private final TimeService timeService;

    @GetMapping
    public ResponseEntity<List<Time>> readTime() {
        return timeService.findTime();
    }

    @PostMapping
    public ResponseEntity<Time> createTime(@RequestBody Time time) {
        return timeService.addTime(time);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return timeService.deleteTime(id);
    }

}
