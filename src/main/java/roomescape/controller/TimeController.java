package roomescape.controller;


import java.net.URI;
import java.util.List;
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
import roomescape.dao.time.TimeJdbcDAO;
import roomescape.entity.Time;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeJdbcDAO timeJdbcDAO;

    public TimeController(TimeJdbcDAO timeJdbcDAO) {
        this.timeJdbcDAO = timeJdbcDAO;
    }

    @PostMapping
    public ResponseEntity<Time> createTimeTable(@RequestBody Time time) {
        Time newTimeTable = timeJdbcDAO.create(time);
        return ResponseEntity.created(URI.create("/times/" + newTimeTable.getId()))
                .body(newTimeTable);
    }

    @GetMapping
    public List<Time> getTimeTables() {
        return timeJdbcDAO.getAll();
    }

    @GetMapping("/{id}")
    public Time getTimeDetail(@PathVariable long id) {
        return timeJdbcDAO.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTimeTable(@PathVariable long id) {
        timeJdbcDAO.delete(id);
    }


}
