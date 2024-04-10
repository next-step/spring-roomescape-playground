package roomescape.controller;


import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import roomescape.dto.request.TimeRequest;
import roomescape.dto.response.TimeResponse;
import roomescape.service.TimeService;

@Controller
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }
    @GetMapping("/time")
    public String getTime() {
        return "time";
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> createTime(@Valid @RequestBody TimeRequest timeRequest) throws
            URISyntaxException {
        Long id = timeService.createTime(timeRequest);
        TimeResponse timeResponse = timeService.getTime(id);
        return ResponseEntity.created(new URI("/times/" + timeResponse.getId())).body(timeResponse);
    }
}