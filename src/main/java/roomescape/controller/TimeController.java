package roomescape.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.exception.NotFoundReservationException;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {

    private TimeService timeService;

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimeInformation() {
        final List<Time> time = timeService.getAllReservation();
        return ResponseEntity.ok().body(time);
    }

    @PostMapping("/times")
    public ResponseEntity<Time> createTime(
            @DateTimeFormat(pattern = "HH:mm")
            @RequestBody Time time
    ) {
        Time newTime = timeService.inserTime(time);

        return ResponseEntity.created(URI.create("/times/" + newTime.getId())).body(newTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable int id){
        timeService.deleteTime(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }
}
