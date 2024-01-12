package roomescape.time.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import roomescape.time.domain.Time;
import roomescape.time.dto.request.TimeRequest;
import roomescape.time.dto.response.TimeResponse;
import roomescape.time.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimes() {
        List<Time> times =  timeService.findTimes();
        return ResponseEntity.ok(times);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse.createTimeDto> createTime (
            @RequestBody @Valid TimeRequest.CreateTimeDto request,
            Errors errors
    ) {
        TimeResponse.createTimeDto response = timeService.saveTime(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/times/"+response.id()))
                .body(response);
    }

    @DeleteMapping("/times/{timeId}")
    public ResponseEntity<Void> deleteTime(
            @PathVariable Long timeId
    ) {
        timeService.removeTime(timeId);
        return ResponseEntity.noContent().build();
    }
}
