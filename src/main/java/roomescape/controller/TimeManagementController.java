package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Time;
import roomescape.dto.TimeCreateForm;
import roomescape.dto.TimeResponseForm;
import roomescape.service.TimeManagementService;

import java.net.URI;
import java.util.List;

@Controller
public class TimeManagementController {

    private final TimeManagementService timeManagementService;

    public TimeManagementController(TimeManagementService timeManagementService) {
        this.timeManagementService = timeManagementService;
    }

    @GetMapping("/time")
    public String time() {
        return "time";
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<TimeResponseForm> createTime(@Valid @RequestBody TimeCreateForm form, BindingResult result) {
        Long newId = timeManagementService.create(form);
        TimeResponseForm newTime = timeManagementService.getTime(newId);

        return ResponseEntity.created(URI.create("/times/" + newId))
                .body(newTime);
    }

    @GetMapping("/times")
    @ResponseBody
    public ResponseEntity<List<TimeResponseForm>> getTimes() {
        List<TimeResponseForm> times = timeManagementService.getTimes();

        return ResponseEntity.ok().body(times);
    }

    @DeleteMapping("times/{id}")
    @ResponseBody
    public ResponseEntity<TimeResponseForm> deleteTime(@PathVariable Long id) {
        timeManagementService.deleteTime(id);

        return ResponseEntity.noContent().build();
    }
}
