package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.TimeEntity;
import roomescape.dto.TimeDTO;
import roomescape.repository.TimeRepository;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {
    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping("/time")
    public String getTimePage(Model model) {
        List<TimeDTO> times = timeRepository.findAll().stream()
                .map(this::entityToDTO)
                .toList();
        model.addAttribute("times", times);
        return "time";
    }

    @GetMapping("/times")
    @ResponseBody
    public List<TimeDTO> getTimes() {
        return timeRepository.findAll().stream()
                .map(this::entityToDTO)
                .toList();
    }

    @PostMapping("/times")
    public ResponseEntity<TimeDTO> addTime(@RequestBody TimeDTO timeDTO) {
        TimeEntity entity = dtoToEntity(timeDTO);
        TimeEntity savedEntity = timeRepository.save(entity);
        TimeDTO savedDTO = entityToDTO(savedEntity);
        return ResponseEntity.created(URI.create("/times/" + savedDTO.id())).body(savedDTO);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private TimeDTO entityToDTO(TimeEntity entity) {
        return new TimeDTO(entity.id(), entity.time());
    }

    private TimeEntity dtoToEntity(TimeDTO dto) {
        return new TimeEntity(dto.id(), dto.time());
    }
}
