package roomescape;

import java.time.LocalTime;
import roomescape.exception.InvalidReservationException;

public record TimeRequest(LocalTime time) {
    public void validate() {
        if (time == null) {
            throw new InvalidReservationException("예약 정보는 비어 있을 수 없습니다.");
        }
    }
    public LocalTime getTime() {
        return time;
    }
}
