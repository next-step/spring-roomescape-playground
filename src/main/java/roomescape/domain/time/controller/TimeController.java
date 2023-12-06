package roomescape.domain.time.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.time.dto.request.CreateTimeRequest;
import roomescape.domain.time.dto.response.GetTimesResponse;
import roomescape.domain.time.entity.Time;
import roomescape.domain.time.service.TimeService;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Controller
@Validated
@RequiredArgsConstructor
public class TimeController {

    private final TimeService timeService;

    @GetMapping("/times")
    public ResponseEntity<List<GetTimesResponse>> findAllTimes() {
        return ResponseEntity.ok(timeService.findAllTimes());
    }

    @PostMapping("/times")
    public ResponseEntity<Void> createTime(@RequestBody @Valid CreateTimeRequest request) {
        final Time savedTime = timeService.saveTime(request.getTime());
        return ResponseEntity.status(CREATED)
                .location(URI.create("/times/" + savedTime.getId()))
                .build();
    }

    @DeleteMapping("/times/{timeId}")
    public ResponseEntity<Void> deleteTimes(@PathVariable @PositiveOrZero long timeId) {
        final long findTimesId = timeService.findTimes(timeId);
        timeService.deleteTimes(findTimesId);
        return ResponseEntity.noContent().build();
    }

}
