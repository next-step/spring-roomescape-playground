package roomescape.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import roomescape.application.ReservationTimeService;
import roomescape.application.dto.ReservationTimeDto;
import roomescape.application.dto.ReservationTimeInfoDto;
import roomescape.presentation.dto.request.ReservationReq;
import roomescape.presentation.dto.request.ReservationTimeReq;
import roomescape.presentation.dto.response.ReservationRes;
import roomescape.presentation.dto.response.ReservationTimeRes;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    @GetMapping("")
    public ResponseEntity<List<ReservationTimeRes>> read() {
        List<ReservationTimeInfoDto> reservationTimeInfoDtos = reservationTimeService.findAll();
        List<ReservationTimeRes> responses = reservationTimeInfoDtos.stream().map(ReservationTimeRes::from).toList();

        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("")
    public ResponseEntity<ReservationTimeRes> create(@Valid @RequestBody final ReservationTimeReq request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new IllegalArgumentException("입력값이 잘못되었습니다");
        }
        final ReservationTimeDto reservationTimeDto = new ReservationTimeDto(request.getTime());
        ReservationTimeInfoDto saved = reservationTimeService.save(reservationTimeDto);
        ReservationTimeRes response = ReservationTimeRes.from(saved);

        return ResponseEntity.created(URI.create("/times/" + saved.getId())).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") final Long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
