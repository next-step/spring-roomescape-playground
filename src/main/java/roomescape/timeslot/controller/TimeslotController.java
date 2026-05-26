package roomescape.timeslot.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.timeslot.dto.request.TimeslotRequest;
import roomescape.timeslot.dto.response.TimeslotResponse;
import roomescape.timeslot.service.TimeslotService;

import java.net.URI;

@RestController
@RequestMapping("/times")
public class TimeslotController {

    private final TimeslotService timeslotService;

    public TimeslotController(TimeslotService timeslotService) {
        this.timeslotService = timeslotService;
    }

    @PostMapping()
    public ResponseEntity<TimeslotResponse> addTimeslot(@Valid @RequestBody TimeslotRequest request) {
        TimeslotResponse newTimeslot = timeslotService.addTimeslot(request);
        URI location = URI.create("times/" + newTimeslot.id());

        return ResponseEntity.created(location).body(newTimeslot);
    }
}
