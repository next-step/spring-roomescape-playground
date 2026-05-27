package roomescape.time;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.time.domain.TimeId;
import roomescape.time.dto.CreateTimeRequest;
import roomescape.time.dto.TimeResponse;
import roomescape.time.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final TimeService service;

    public TimeController(TimeService service) {
        this.service = service;
    }

    @GetMapping
    public List<TimeResponse> getTimes() {
        return service.getTimes();
    }

    @PostMapping
    public ResponseEntity<TimeResponse> createTime(@RequestBody @Valid CreateTimeRequest body) {
        TimeResponse result = service.createTime(body);

        return ResponseEntity.created(URI.create("/times/" + result.id().id()))
                .body(result);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTime(@PathVariable long id) {
        TimeId timeId = new TimeId(id);
        service.deleteTime(timeId);
    }
}
