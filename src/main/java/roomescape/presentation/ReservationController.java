package roomescape.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import roomescape.application.ReservationService;
import roomescape.application.dto.ReservationDto;
import roomescape.application.dto.ReservationInfoDto;
import roomescape.presentation.dto.request.ReservationReq;
import roomescape.presentation.dto.response.ReservationRes;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("")
    public ResponseEntity<List<ReservationRes>> read() {
        List<ReservationInfoDto> reservationInfoDtos = reservationService.findAll();
        List<ReservationRes> responses = reservationInfoDtos.stream()
                .map(ReservationRes::from)
                .toList();
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("")
    public ResponseEntity<ReservationRes> create(@Valid @RequestBody final ReservationReq request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new IllegalArgumentException("입력값이 잘못되었습니다");
        }

        final ReservationDto reservationDto = new ReservationDto(
                request.getName(),
                request.getDate(),
                request.getTime()
        );
        ReservationInfoDto saved = reservationService.save(reservationDto);
        ReservationRes response = ReservationRes.from(saved);

        return ResponseEntity.created(URI.create("/reservations/" + saved.getId())).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
