package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.TimeDAO;
import roomescape.dto.Time;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {

    private TimeDAO timeDAO;

    public TimeController(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> read() {
        // TODO: 저장된 모든 time 정보를 반환한다.
        return ResponseEntity.ok(timeDAO.findAllTimes());
    }

    @PostMapping("/times")
    public ResponseEntity<Time> create(@RequestBody Time time) {
        // TODO: time 정보를 받아서 생성한다.
        Long id = timeDAO.insertWithKeyHolder(time);
        Time newTime = Time.toEntity(time.getTime(), id);
        return ResponseEntity.created(URI.create("/times/" + id)).body(newTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // TODO: url 상의 id 정보를 받아 time을 삭제한다.
        timeDAO.delete(id);
        return ResponseEntity.noContent().build();
    }

}