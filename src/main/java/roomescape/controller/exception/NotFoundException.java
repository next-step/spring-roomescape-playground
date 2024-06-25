package roomescape.controller.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String errormessage) {
        super(errormessage);
    }
}
