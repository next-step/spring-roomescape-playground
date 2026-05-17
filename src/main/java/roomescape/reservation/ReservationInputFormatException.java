package roomescape.reservation;

import org.springframework.http.ResponseEntity;
import roomescape.global.controller.ApiException;

public class ReservationInputFormatException extends ApiException {
    private final String field;

    public ReservationInputFormatException(String field, String message) {
        super(message);
        this.field = field;
    }

    @Override
    public ResponseEntity<? extends Dto> getResponse() {
        Dto dto = new Dto();
        dto.field = field;
        return ResponseEntity.badRequest().body(dto);
    }

    public class Dto extends ApiException.Dto {
        public String field;

        @Override
        public String getType() {
            return "Reservation.InputFormat";
        }
    }
}
