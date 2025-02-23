package roomescape.time.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.time.Time;
import roomescape.time.TimeDao;
import roomescape.time.dto.TimeCreateRequest;
import roomescape.time.dto.TimeCreateResponse;
import roomescape.time.exception.TimeAlreadyExistException;
import roomescape.time.exception.TimeNotFoundException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class TimeCommandController {

    private final TimeDao timeDao;

    public TimeCommandController(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @GetMapping("/times/{id}")
    public Time getTime(@PathVariable("id") long id) {
        return timeDao.findById(id)
                .orElseThrow(TimeNotFoundException::new);
    }

    @GetMapping("/times")
    public List<Time> getTimes() {
        return timeDao.findAll();
    }

    @PostMapping("/times")
    public ResponseEntity<TimeCreateResponse> createTime(@RequestBody TimeCreateRequest request) throws URISyntaxException {

        Time newTime = Time.ofNew(request.getTime());

        Optional<Time> optionalTime = timeDao.findByTime(newTime);
        if (optionalTime.isPresent()) {
            throw new TimeAlreadyExistException();
        }

        Time time = timeDao.save(newTime);

        URI uri = new URI("/times/" + time.getId());
        return ResponseEntity
                .created(uri)
                .body(new TimeCreateResponse(
                        time.getId(),
                        time.getTime()
                ));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") long id) {
        timeDao.delete(id);

        return ResponseEntity.noContent().build();
    }
}
