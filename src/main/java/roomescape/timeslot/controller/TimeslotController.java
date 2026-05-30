package roomescape.timeslot.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.timeslot.dto.request.TimeslotRequest;
import roomescape.timeslot.dto.response.TimeslotResponse;
import roomescape.timeslot.service.TimeslotService;

import java.net.URI;
import java.util.List;

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
        URI location = URI.create("/times/" + newTimeslot.id());

        return ResponseEntity.created(location).body(newTimeslot);
    }

    @GetMapping()
    public ResponseEntity<List<TimeslotResponse>> getAllTimeslots() {
        return ResponseEntity.ok(timeslotService.getAllTimeslots());
    }

    @GetMapping("/{timeId}")
    public ResponseEntity<TimeslotResponse> getTimeslotById(@PathVariable Long timeId) {
        return ResponseEntity.ok(timeslotService.getTimeslotById(timeId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeslotById(@PathVariable Long id) {
        timeslotService.deleteTimeslotById(id);
        return ResponseEntity.noContent().build();
    }
}
