package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.Repository.TimeRepository;
import roomescape.domain.Time;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    // 시간데이터 조회
    @GetMapping
    public ResponseEntity<List<Time>> getTime() {
        List<Time> times = timeRepository.findAll();
        return ResponseEntity.ok(times);
    }

    // 시간 데이터 추가
    @PostMapping
    public ResponseEntity<Time> addTime(@RequestBody Time time) {
        Time savedTime = timeRepository.save(time);
        URI uri = URI.create("/times/" + savedTime.getId());
        return ResponseEntity.created(uri).body(savedTime);
    }

    // 시간 데이터 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTime(@PathVariable Long id) {
        timeRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("시간 데이터가 삭제되었습니다.");
    }
}
