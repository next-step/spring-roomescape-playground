package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import roomescape.dto.TimeDto;
import roomescape.model.Times;
import roomescape.model.Time;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(TimeController.TIME_API_ENDPOINT_ROOT)
public class TimeController {

    public final static String TIME_API_ENDPOINT_ROOT = "/times";
    private final Times times;

    @Autowired
    public TimeController(Times times) {
        this.times = times;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Time> getAllTimes() {
        return this.times.getTimes();
    }

    @PostMapping
    public ResponseEntity<Time> createTime(@RequestBody @Valid TimeDto timeDto) {
        Time newTIme= this.times.add(timeDto);

        return ResponseEntity
                .created(URI.create(TIME_API_ENDPOINT_ROOT + "/" + newTIme.id()))
                .body(newTIme);
    }

    @DeleteMapping("/{deletingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTime(@PathVariable Long deletingId) {
        this.times.removeById(deletingId);
    }
}
