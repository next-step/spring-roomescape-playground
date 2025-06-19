package roomescape.exception;

public class NotFoundException extends IllegalArgumentException {
    public NotFoundException(Long id) {
        super("존재하지 않는 예약입니다. id : " + id);
    }
}
