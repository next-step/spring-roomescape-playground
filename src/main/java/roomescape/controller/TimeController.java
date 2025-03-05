package roomescape.controller;


import jakarta.servlet.http.HttpServletResponse;
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
import roomescape.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Time createTimeTable(@RequestBody Time time, HttpServletResponse response) {
        Time newTimeTable = timeService.createTime(time);
        response.setHeader("Location", "/times/" + newTimeTable.getId());
        return newTimeTable;
    }

    @GetMapping
    public List<Time> getTimeTables() {
        return timeService.getAllTimes();
    }

    @GetMapping("/{id}")
    public Time getTimeDetail(@PathVariable long id) {
        return timeService.getTimeById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTimeTable(@PathVariable long id) {
        timeService.deleteTime(id);
    }


}
