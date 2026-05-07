package roomescape;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import roomescape.exceptions.AlreadyReservedException;
import roomescape.exceptions.PastDateTimeException;
import roomescape.exceptions.ReservationNotFoundException;

public enum ErrorCode {
    METHOD_ARGUMENT_NOT_VALID(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST,
            e -> Optional.ofNullable(((MethodArgumentNotValidException) e).getFieldError())
                    .map(FieldError::getDefaultMessage).orElse("")),
    ALREADY_RESERVED(AlreadyReservedException.class, HttpStatus.BAD_REQUEST, Throwable::getMessage),
    PAST_DATETIME(PastDateTimeException.class, HttpStatus.BAD_REQUEST, Throwable::getMessage),
    RESERVATION_NOT_FOUND(ReservationNotFoundException.class, HttpStatus.NOT_FOUND, Throwable::getMessage);

    private final Class<? extends Exception> exception;
    private final HttpStatus httpStatus;
    private final Function<Exception, String> getMessage;

    private static final Map<Class<? extends Exception>, ErrorCode> BY_CLASS = Stream.of(values()).collect(
            Collectors.toMap(ErrorCode::getException, e -> e));

    ErrorCode(Class<? extends Exception> exception, HttpStatus httpStatus,
              Function<Exception, String> getMessage) {
        this.exception = exception;
        this.httpStatus = httpStatus;
        this.getMessage = getMessage;
    }

    public static ErrorCode valueOfException(Exception exception) {
        return BY_CLASS.get(exception.getClass());
    }

    public String getMessage(Exception exception) {
        return getMessage.apply(exception);
    }

    public Class<? extends Exception> getException() {
        return exception;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
