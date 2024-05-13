package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "roomescape.controller") // 해당 패키지에 있는 컨트롤러에만 적용
public class ReservationExceptionHandler {

    @ExceptionHandler // 생략 시, 메서드의 매개변수 타입으로 자동 지정됨
    public ResponseEntity handleException(ReservationException re) {
        return ResponseEntity.status(re.getErrorMessage().getStatus())
                .body(re.getErrorMessage().getMessage());
    }

}
