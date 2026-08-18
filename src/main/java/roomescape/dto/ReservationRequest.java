package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.exception.BlankReservationException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    private String name;
    private String date;
    private String time;

    public Reservation toReservation() {
        if (date == null || date.isBlank() || time == null || time.isBlank()) {
            throw new BlankReservationException("날짜와 시간을 입력해주세요.");
        }

        try {
            LocalDate parsedDate = LocalDate.parse(date);
            LocalTime parsedTime = LocalTime.parse(time);

            return new Reservation(0, name, parsedDate, parsedTime);
        } catch (DateTimeException e) {
            throw new BlankReservationException("날짜나 시간의 형식이 올바르지 않습니다.");
        }
    }

    public ReservationRequest() {
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
