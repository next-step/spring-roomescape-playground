package roomescape.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import roomescape.global.controller.ApiException;

public class ReservationDoesNotExistException extends ApiException {
    public ReservationDoesNotExistException() {
        super("해당 예약이 존재하지 않습니다.");
    }

    @Override
    public ResponseEntity<? extends Dto> getResponse() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Dto());
    }


    public class Dto extends ApiException.Dto {
        @Override
        public String getType() {
            return "Reservation.DoesNotExist";
        }
    }
}
