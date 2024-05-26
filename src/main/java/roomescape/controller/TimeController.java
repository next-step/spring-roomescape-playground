package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.DAO.TimeDAO;

import javax.sql.DataSource;
import java.net.URI;
import java.util.List;

@Controller
public class TimeController {
    private final TimeDAO TimeDAO;

    public TimeController(DataSource dataSource) {
        this.TimeDAO = new TimeDAO(dataSource);
    }

    @GetMapping("/time")
    public String times() {
        return "time";
    }

    @PostMapping("/times")
    public ResponseEntity<Time> addTime(@RequestBody Time request) {
        long id = TimeDAO.save(request);
        Time time = TimeDAO.findById(id);
        URI location = URI.create("/times/" + id);

        return ResponseEntity.created(location).body(time);
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimes() {
        List<Time> timeList = TimeDAO.findAll();
        return ResponseEntity.ok(timeList);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable long id) {
        long deletedId = TimeDAO.deleteById(id);
        if (deletedId == -1) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}