package roomescape.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import roomescape.global.controller.ApiException;

public class ReservationTimeNotFoundException extends ApiException {
    public ReservationTimeNotFoundException() {
        super("예약 시간이 사전에 정의되지 않았습니다. 필요하다면 시간 관리에서 해당 시간을 생성해주세요.");
    }

    @Override
    public ResponseEntity<? extends Dto> getResponse() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Dto());
    }

    public class Dto extends ApiException.Dto {
        @Override
        public String getType() {
            return "Reservation.TimeNotFound";
        }
    }
}
