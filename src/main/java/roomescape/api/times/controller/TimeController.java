package roomescape.api.times.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.api.times.dto.request.TimeRegisterRequest;
import roomescape.api.times.dto.response.TimeResponse;
import roomescape.api.times.service.TimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class TimeController {

    private final TimeService timeService;

    @GetMapping
    public ResponseEntity<List<TimeResponse>> getTimes() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(timeService.getTimes());
    }

    @PostMapping
    public ResponseEntity<TimeResponse> createTime(
            @RequestBody final TimeRegisterRequest timeRegisterRequest
    ) {
        final TimeResponse timeResponse = timeService.createTime(
                timeRegisterRequest.time()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/times/" + timeResponse.id())
                .body(timeResponse);
    }

    @DeleteMapping("/{timeId}")
    public ResponseEntity<Void> deleteTime(
            @PathVariable(name = "timeId") final long timeId
    ) {
        timeService.deleteTime(timeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}
