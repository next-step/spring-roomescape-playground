package roomescape.time.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.time.db.TimeEntity;
import roomescape.time.model.TimeRequest;
import roomescape.time.service.TimeService;

import java.net.URI;
import java.sql.Time;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TimeApiController {

    private final TimeService timeService;

    @GetMapping("/time")
    public String timePage() {
        return "time";
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeEntity>> times() {

        List<TimeEntity> entityLists = timeService.findAllList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(entityLists);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeEntity> makeTimes(
            @Valid
            @RequestBody
            TimeRequest timeRequest
    ) {

        TimeEntity timeEntity = timeService.insertTime(timeRequest);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/times/" + timeEntity.getId()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(httpHeaders)
                .body(timeEntity);
    }

    @DeleteMapping("times/{id}")
    public ResponseEntity<Void> deleteTimes(
            @PathVariable Long id
    ) {

        timeService.deleteTime(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
