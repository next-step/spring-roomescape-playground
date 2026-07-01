package roomescape.dataLayer.errors;

public class ReservationNotFoundException extends IllegalArgumentException {
    public ReservationNotFoundException(String s) {
        super(s);
    }
}
