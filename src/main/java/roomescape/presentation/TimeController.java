package roomescape.presentation;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.application.TimeService;
import roomescape.application.dto.CreateInfoTimeDto;
import roomescape.application.dto.CreateTimeDto;
import roomescape.application.dto.ReadTimeDto;
import roomescape.presentation.dto.request.CreateTimeRequest;
import roomescape.presentation.dto.response.CreateTimeResponse;
import roomescape.presentation.dto.response.ReadTimeResponse;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {

    private final TimeService timeService;

    public TimeController(final TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/time")
    public String time() {
        return "time.html";
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<CreateTimeResponse> create(@RequestBody @Valid final CreateTimeRequest request) {
        final CreateTimeDto createTimeDto = new CreateTimeDto(request.getTime());
        final CreateInfoTimeDto createInfoTimeDto = timeService.create(createTimeDto);
        final CreateTimeResponse response = CreateTimeResponse.from(createInfoTimeDto);

        return ResponseEntity.created(URI.create("/times/" + createInfoTimeDto.getId()))
                             .body(response);
    }

    @GetMapping("/times")
    @ResponseBody
    public ResponseEntity<List<ReadTimeResponse>> readAll() {
        final List<ReadTimeDto> readTimeDtos = timeService.readAll();
        final List<ReadTimeResponse> responses = readTimeDtos.stream()
                                                             .map(ReadTimeResponse::from)
                                                             .toList();

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/times/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        timeService.deleteById(id);

        return ResponseEntity.noContent()
                             .build();
    }
}
