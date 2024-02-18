package roomescape.data.entity;

import static java.util.Objects.isNull;

import lombok.Builder;
import lombok.Getter;
import roomescape.common.exception.ReservationErrorCode;
import roomescape.common.exception.ReservationException;

@Builder
@Getter
public class Reservation {

    private Long id;
    private String name;
    private String date;
    private ReservationTime time;

    public Reservation(Long id, String name, String date, ReservationTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(String name, String date, ReservationTime time) {
        if (name.isBlank() || date.isBlank()) {
            throw new ReservationException(ReservationErrorCode.INVALID_ARGUMENT_ERROR);
        }
        if (isNull(time) || isNull(time.getId())) {
            throw new IllegalArgumentException("예약을 생성할 수 없습니다. 존재하지 않는 예약시간입니다.");
        }
    }
}
