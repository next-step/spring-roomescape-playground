package roomescape.reservation.presentation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.MainPageController;

@Slf4j
@ControllerAdvice(assignableTypes = MainPageController.class)
public class MainPageExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        log.error("error: " + e.getMessage());
        return "error/500"; //view 렌더링 페이지는 만들지 않음!
    }
}
