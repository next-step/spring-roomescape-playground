package roomescape.exception;

import roomescape.utils.ErrorMessage;

public class RoomEscapeException extends RuntimeException {
    public RoomEscapeException(ErrorMessage message) {
        super(message.getMessage());
    }
}
