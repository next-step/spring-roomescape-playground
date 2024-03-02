package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class TimeController {

    @Autowired
    private TimeDao timeDao;

    public TimeController(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @GetMapping("/time")
    public String time() {
        return "time";
    }

    @GetMapping("/times")
    @ResponseBody
    public ResponseEntity<List<Time>> read() {
        List<Time> times = timeDao.getAllTimes();
        return ResponseEntity.ok().body(times);
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<Time> create(@RequestBody Time time) {
        Time newTime = timeDao.insertTime(time);
        return ResponseEntity.created(URI.create("/times/" + newTime.getId())).body(newTime);
    }

    @DeleteMapping("/times/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int removed = timeDao.deleteTime(id);
        if (removed == 0) {
            throw new NoSuchElementException("삭제할 항목이 없습니다.");
        }
        return ResponseEntity.noContent().build();
    }
}
