package com.cholog.roomescape.domain.controller;

import com.cholog.roomescape.domain.dto.request.TimeRequest;
import com.cholog.roomescape.domain.dto.response.TimeResponse;
import com.cholog.roomescape.domain.entity.Time;
import com.cholog.roomescape.domain.service.TimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeApiController {

    private final TimeService timeService;

    public TimeApiController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> getTimes() {
        List<TimeResponse> responses = timeService.findAllTime().stream()
                .map(TimeResponse::toDto)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<TimeResponse> postTime(
            @RequestBody TimeRequest request
    ) {
        Time createdTime = timeService.createTime(request.time());

        return ResponseEntity
                .created(URI.create("/times/" + createdTime.getId()))
                .body(TimeResponse.toDto(createdTime));
    }

    @DeleteMapping("/{timeId}")
    public ResponseEntity<Void> deleteTime(
            @PathVariable Long timeId
    ) {
        timeService.deleteTime(timeId);
        return ResponseEntity.noContent().build();
    }
}
