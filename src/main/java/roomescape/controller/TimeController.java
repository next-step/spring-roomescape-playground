package roomescape.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.service.TimeService;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class TimeController {
    private final TimeService timeService;

    // 예약 조회 (localhost:8080/times 요청 시 시간 예약자 현황 리스트가 응답할 수 있도록 구현)
    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimes() {
        // DB에서 예약 정보를 가져와서 반환
        List<Time> response = timeService.getTimes();
        return ResponseEntity.ok()
                .body(response);
    }

    // 예약 추가
    @PostMapping("/times")
    public  ResponseEntity<?> saveTime(@RequestBody Time time) {
        // 예약 추가 시 필요한 인자값이 비어있는 경우, 예외를 던집니다.
        if (Objects.equals(time.getTime(), "")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error! 예약 추가 시 필요한 인자값이 비어 있습니다.");
        }

        // 예약 추가
        Time savedTime = timeService.saveTime(time.getTime());

        // 생성된 예약 정보와 함께 201 Created 응답 반환 (CREATED : 201, body : API 응답 정보 반환)
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/times/" + savedTime.getId())
                .body(savedTime);
    }

    // 예약 삭제
    @DeleteMapping("/times/{id}") // 'reservaions/1' 이런 식으로 맵핑함
    public ResponseEntity<?> deleteTime(@PathVariable Long id) {
        // ID와 일치하는 예약을 찾아서 삭제합니다.
        Time existingTIme = timeService.getTime(id);

        if (existingTIme != null) {
            timeService.deleteTime(id);
            return ResponseEntity.noContent().build();
        } else {
            // ID와 일치하는 예약을 찾지 못한 경우, 예외를 던집니다.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error! 회원 ID와 일치하는 예약을 찾지 못했습니다.");
        }
    }
}
