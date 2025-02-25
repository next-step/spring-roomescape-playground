package roomescape.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.request.CreateTimeRequest;
import roomescape.dto.response.TimeResponse;
import roomescape.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {

    private static final String HEADER_LOCATION = "Location";
    private static final String LOCATION_DEFAULT_VALUE = "/times/";

    private final TimeService timeService;

    public TimeController(final TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<TimeResponse> createTime(@RequestBody CreateTimeRequest request) {
        TimeResponse response = timeService.createTime(request);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_LOCATION, LOCATION_DEFAULT_VALUE + response.id());

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(response);
    }
}
