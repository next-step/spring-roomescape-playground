package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Time;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    // 시간 관리 페이지
    @GetMapping("/time")
    public String reservationPage() {
        return "time";
    }

    // 시간 조회
    @GetMapping("/times")
    @ResponseBody
    public ResponseEntity<List<Time>> getTimes() {
        List<Time> times = timeService.getAllTimes();
        return ResponseEntity.ok(times);
    }

    // 시간 추가
    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<Time> addTime(@RequestBody Time request) {
        Time newTime = timeService.addTime(request);
        return ResponseEntity.created(URI.create("/times/" + newTime.getId())).body(newTime);
    }

    // 시간 삭제
    @DeleteMapping("/times/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
