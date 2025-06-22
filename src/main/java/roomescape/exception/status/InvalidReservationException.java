package roomescape.exception.status;

import java.util.List;

public class InvalidReservationException extends RuntimeException {
    private final List<String> messages;

    public InvalidReservationException(List<String> messages) {
        super(String.join(", ", messages));
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}