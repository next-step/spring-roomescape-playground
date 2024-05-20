package roomescape.exception;

public class InvalidReservationInputException extends RuntimeException {
    public InvalidReservationInputException(String message, String invalidInput) {
        super(message + invalidInput);
    }
}
