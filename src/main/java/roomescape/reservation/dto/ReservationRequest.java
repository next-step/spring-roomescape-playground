package roomescape.reservation.dto;

import lombok.Getter;
import lombok.Setter;
import roomescape.reservation.exception.BadRequestReservationException;
import roomescape.time.domain.Time;


@Getter
@Setter
public class ReservationRequest {
    private String name;
    private String date;
    private Long timeId;

    public void validate() {
        if (name == null || name.isEmpty()) {
            throw new BadRequestReservationException("예약 이름은 필수 입력값입니다.");
        }

        if (date == null) {
            throw new BadRequestReservationException("예약 날짜는 필수 입력값입니다.");
        }

        if (timeId == null) {
            throw new BadRequestReservationException("예약 시간은 필수 입력값입니다.");
        }
    }

}
