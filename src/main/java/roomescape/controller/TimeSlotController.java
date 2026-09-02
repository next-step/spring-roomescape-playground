package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.TimeSlot;
import roomescape.dto.TimeSlotRequest;
import roomescape.dto.TimeSlotResponse;
import roomescape.service.TimeSlotService;

import java.net.URI;
import java.util.List;

@RestController
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @GetMapping("/times")
    public List<TimeSlotResponse> getTimeSlots() {
        return timeSlotService.findAll().stream()
                .map(TimeSlotResponse::from)
                .toList();
    }

    @PostMapping("/times")
    public ResponseEntity<TimeSlotResponse> createTimeSlot(
            @RequestBody TimeSlotRequest request
    ) {
        TimeSlot saved = timeSlotService.save(request.toTimeSlot());

        return ResponseEntity
                .created(URI.create("/times/" + saved.getId()))
                .body(TimeSlotResponse.from(saved));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTimeSlot(
            @PathVariable Long id
    ) {
        timeSlotService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
