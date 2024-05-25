package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.domain.TimeRepository;

import javax.sql.DataSource;
import java.net.URI;
import java.util.List;

@Controller
public class TimeController {
    private final TimeRepository timeRepository;

    public TimeController(DataSource dataSource) {
        this.timeRepository = new TimeRepository(dataSource);
    }

    @GetMapping("/time")
    public String times() {
        return "time";
    }

    @PostMapping("/times")
    public ResponseEntity<Time> addTime(@RequestBody Time request) {
        long id = timeRepository.save(request);
        Time time = timeRepository.findById(id);
        URI location = URI.create("/times/" + id);

        return ResponseEntity.created(location).body(time);
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimes() {
        List<Time> timeList = timeRepository.findAll();
        return ResponseEntity.ok(timeList);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable long id) {
        long deletedId = timeRepository.deleteById(id);
        if (deletedId == -1) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}