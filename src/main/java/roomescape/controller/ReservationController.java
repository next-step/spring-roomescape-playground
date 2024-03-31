package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.Reservation;
import roomescape.dto.ReservationDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController
{
    private List<Reservation> reservations = new ArrayList<>(); // 예약 정보들이 들어있는 리스트
    private AtomicLong index = new AtomicLong(1); // 예약자 번호

    // localhost:8080/reservations 요청 시 예약자 현황 리스트가 응답할 수 있도록 구현
    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservations;
    }

    // 예약 추가
    @PostMapping("/reservations")
    public  ResponseEntity<Reservation> saveReservation(@RequestBody Reservation reservation) {
        // 예약 추가 시 필요한 인자값이 비어있는 경우, 예외를 던집니다.
        if (Objects.equals(reservation.getName(), "") || Objects.equals(reservation.getDate(), "") || Objects.equals(reservation.getTime(), "")) {
            return ResponseEntity.badRequest().build();
        }
        // 객체를 할당하고 리스트에 넣자! (index의 initialvalue가 1이기에 getAndIncrement를 해야함)
        Reservation newReservation = new Reservation(index.getAndIncrement(), reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.add(newReservation);

        // 생성된 예약 정보와 함께 201 Created 응답 반환 (CREATED : 201, body : API 응답 정보 반환)
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + newReservation.getId())
                .body(newReservation);
    }


    // 예약 취소
    @DeleteMapping("/reservations/{id}") // 'reservaions/1' 이런 식으로 맵핑함
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long id) {
        // ID와 일치하는 예약을 찾아서 삭제합니다.
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(id)) {
                reservations.remove(reservation);
                return ResponseEntity.noContent().build();
            }
        }

        // ID와 일치하는 예약을 찾지 못한 경우, 예외를 던집니다.
        return ResponseEntity.badRequest().build();
    }

}