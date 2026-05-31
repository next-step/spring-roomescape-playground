package roomescape.reservation;

import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import roomescape.global.controller.ApiException;
import roomescape.reservation.domain.ReservationId;

public class ReservationDuplicateTimeException extends ApiException {
    private final ReservationId previous;

    public ReservationDuplicateTimeException(@Nullable ReservationId previous) {
        super("해당 예약 날짜/시간에 이미 다른 예약이 있습니다.");
        this.previous = previous;
    }

    @Override
    public ResponseEntity<? extends Dto> getResponse() {
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.CONFLICT);
        if (previous != null) {
            builder.header("Location", "/reservations/" + previous.id());
        }
        return builder.body(new Dto());
    }


    public class Dto extends ApiException.Dto {
        @Override
        public String getType() {
            return "Reservation.DuplicateTime";
        }
    }
}
