package roomescape.controller;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String errormessage) {
        super(errormessage);
    }
}
