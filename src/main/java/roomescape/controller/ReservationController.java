package roomescape.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationReqDto;
import roomescape.dto.ReservationResDto;
import roomescape.exception.BadRequestException;
import roomescape.model.Reservation;
import roomescape.template.ApiResponseTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping(value = "/reservations", produces = "text/html")
    public String reservationPage() {
        return "new-reservation";
    }

    @GetMapping(value = "/reservations", produces = "application/json")
    @ResponseBody
    public ApiResponseTemplate<List<ReservationResDto>> getReservations() {
        List<ReservationResDto> dtoList = reservations.stream()
                .map(reservation -> new ReservationResDto(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime()))
                .collect(Collectors.toList());

        return new ApiResponseTemplate<>("success", dtoList);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ApiResponseTemplate<ReservationResDto>> addReservation(@RequestBody ReservationReqDto reservationReqDto) {
        validateReservationRequest(reservationReqDto);

        Reservation newReservation = new Reservation(
                index.getAndIncrement(),
                reservationReqDto.getName(),
                reservationReqDto.getDate(),
                reservationReqDto.getTime()
        );
        reservations.add(newReservation);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/reservations/" + newReservation.getId());

        ReservationResDto newReservationResDto = new ReservationResDto(
                newReservation.getId(),
                newReservation.getName(),
                newReservation.getDate(),
                newReservation.getTime()
        );

        return new ResponseEntity<>(
                new ApiResponseTemplate<>("success", newReservationResDto),
                headers,
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponseTemplate<String>> deleteReservation(@PathVariable Long id) {
        ensureReservationExists(id);
        removeReservation(id);
        return createSuccessDeleteResponse();
    }

    private ResponseEntity<ApiResponseTemplate<String>> createSuccessDeleteResponse() {
        return new ResponseEntity<>(new ApiResponseTemplate<>("success", "예약 삭제 성공"), HttpStatus.NO_CONTENT);
    }

    private void ensureReservationExists(Long id) {
        if (!reservationExists(id)) {
            throw new BadRequestException("예약 삭제 실패");
        }
    }

    private void validateReservationRequest(ReservationReqDto reservationReqDto) {
        if (reservationReqDto.getName() == null || reservationReqDto.getName().isEmpty()) {
            throw new BadRequestException("이름을 작성해주세요");
        }
        if (reservationReqDto.getDate() == null) {
            throw new BadRequestException("날짜를 선택해주세요");
        }
        if (reservationReqDto.getTime() == null || reservationReqDto.getTime().isEmpty()) {
            throw new BadRequestException("시간을 선택해주세요");
        }
    }

    private boolean reservationExists(Long id) {
        return reservations.stream().anyMatch(reservation -> reservation.getId().equals(id));
    }

    private void removeReservation(Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

    @PostMapping("/reservations/reset")
    @ResponseBody
    public ResponseEntity<Void> resetReservations() {
        index.set(1);
        reservations.clear();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
