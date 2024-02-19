package roomescape.exception.Time;

public class TimeException extends RuntimeException {

    public TimeException(TimeErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
