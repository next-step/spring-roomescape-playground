package roomescape.time.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.time.dto.TimeRequest;
import roomescape.time.dto.TimeResponse;
import roomescape.time.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TimeApiController {

    private final TimeService timeService;
    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> times(){

        List<TimeResponse> times = timeService.findAlltime();
        return ResponseEntity.status(HttpStatus.OK)
                .body(times);
    }
    @PostMapping("/times")
    public ResponseEntity<TimeResponse> addTimes(
            @Valid
            @RequestBody TimeRequest timeRequest
    ) {
        TimeResponse timeResponse = timeService.addTime(timeRequest);
        return ResponseEntity.created(URI.create("/times/" + timeResponse.getId())).body(timeResponse);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<?> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }

}
