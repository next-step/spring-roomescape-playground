package roomescape.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.exception.BadRequestException;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReservationApiController {

    private final ReservationService reservationService;

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> reservations(){
        List<ReservationResponseDto> responseDtoList = reservationService.findAllReservations();

        return ResponseEntity.ok(responseDtoList);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> addReservation(@RequestBody ReservationRequestDto reservationRequestDto){
        try {
            ReservationResponseDto responseDto = reservationService.addReservation(reservationRequestDto);
            return ResponseEntity.created(URI.create("/reservations/" + responseDto.getId()))
                    .body(responseDto);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("예약 추가 중 오류가 발생했습니다.", e);
        }
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancleReservation(@PathVariable Long id){
        try {
            boolean canceled = reservationService.cancelReservation(id);
            if (canceled) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("예약 취소 중 오류가 발생했습니다.", e);
        }
    }
}
