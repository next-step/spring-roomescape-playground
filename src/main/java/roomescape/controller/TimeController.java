package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.domain.time.dto.RequestTimeDTO;
import roomescape.domain.time.Time;
import roomescape.domain.time.dao.TimeDAO;
import roomescape.domain.time.dto.TimeDTO;

@Controller
@RequestMapping(value = "/times")
public class TimeController {
    private final TimeDAO timeDAO;

    @Autowired
    public TimeController(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    @GetMapping
    public ResponseEntity<List<TimeDTO>> reservations() {
        List<TimeDTO> times = timeDAO.findAll();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(times, headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Time> create(@RequestBody RequestTimeDTO request) {
        Time time = request.toTime();
        Time newTime = timeDAO.insert(time);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/times/" + Long.toString(newTime.getId())));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(newTime);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        timeDAO.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
