package com.cholog.roomescape.roomescape.controller;

import com.cholog.roomescape.roomescape.dto.request.TimeRequest;
import com.cholog.roomescape.roomescape.dto.response.TimeResponse;
import com.cholog.roomescape.roomescape.entity.Time;
import com.cholog.roomescape.roomescape.service.TimeService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalTime;
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
    public ResponseEntity<?> postTime(
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
