package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.TimeRequest;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class TimeController {

    private final TimeDao timeDao;

    public TimeController(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @PostMapping("/times")
    public ResponseEntity<Void> createTime(@Valid @RequestBody TimeRequest request) throws URISyntaxException {
        Time saved = timeDao.add(new Time(request.time()));
        return ResponseEntity.created(new URI("/times/" + saved.getId()))
                .build();
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> showTimes() {
        return ResponseEntity.ok(timeDao.getAll());
    }

    @DeleteMapping("/times/{timeId}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long timeId) {
        timeDao.delete(timeId);

        return ResponseEntity.noContent().build();
    }
}
