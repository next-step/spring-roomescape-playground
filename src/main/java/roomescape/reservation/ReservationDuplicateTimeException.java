package roomescape.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import roomescape.global.controller.ApiException;

public class ReservationDuplicateTimeException extends ApiException {
    public ReservationDuplicateTimeException() {
        super("해당 예약 시간에 이미 다른 예약이 있습니다.");
    }

    @Override
    public ResponseEntity<? extends Dto> getResponse() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new Dto());
    }


    public class Dto extends ApiException.Dto {
        @Override
        public String getType() {
            return "Reservation.DuplicateTime";
        }
    }
}
