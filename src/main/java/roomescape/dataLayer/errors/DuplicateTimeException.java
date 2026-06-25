package roomescape.dataLayer.errors;

public class DuplicateTimeException extends IllegalArgumentException{
    public DuplicateTimeException(String message) {
        super(message);
    }
}
