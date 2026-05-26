package roomescape;

import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/times")
public class TimeController {

    private static final Logger log = LoggerFactory.getLogger(TimeController.class);
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<Time> create(@RequestBody Time time) {
        log.info("시간 생성 요청: time={}", time.getTime());
        Time newTime = timeService.createTime(time);
        return ResponseEntity.created(URI.create("/times/" + newTime.getId())).body(newTime);
    }

    @GetMapping
    public ResponseEntity<List<Time>> readAll() {
        log.info("전체 시간 조회 요청");
        List<Time> timeList = timeService.findAllTimes();
        return ResponseEntity.ok(timeList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("시간 삭제 요청: id={}", id);
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
