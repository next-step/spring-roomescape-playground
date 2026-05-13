package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.Reservation;
import roomescape.ReservationRequest;
import roomescape.exception.BadRequestException;
import roomescape.exception.NotFoundReservationException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ApiController {

    private final List<Reservation> reservations = new CopyOnWriteArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {//예약 목록을 조회
        return reservations; //현재 저장된 예약 리스트 전체를 응답으로 보냄
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest request) {//reservation객체로 바꿔서 받음
        if(request.getName().isEmpty() || request.getDate().isEmpty() || request.getTime().isEmpty()){
            throw new BadRequestException(("필수 입력 값이 누락되었습니다."));
        }
        Reservation reservation = new Reservation(//새 예약 객체 만듦
                index.incrementAndGet(), //id를 1 증가시키고 값을 가져옴
                request.getName(),
                request.getDate(),
                request.getTime()
        );
        reservations.add(reservation);
        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId())) //http 상태코드 201을 응답하고, 새로 만든 예약 위치를 /reservations/1같은 주소로 알려줌
                .body(reservation);//응답 본문에 새로 생성된 예약 객체를 담아서 보냄
    }

    @DeleteMapping("/reservations/{id}") //삭제 요청
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id){//주소에 있는 id값을 꺼내서 변수 id에 넣음
        Reservation reservation = reservations.stream()
                        .filter(it -> it.getId().equals(id))
                                .findFirst()
                                        .orElseThrow(() -> new NotFoundReservationException("삭제할 예약을 찾을 수 없습니다."));


        reservations.remove(reservation);
        return ResponseEntity.noContent().build(); //삭제 성공 후 응답 보내기. 204 no content 상태 코드를 반환
    }
}
