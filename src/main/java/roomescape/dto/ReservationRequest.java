package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.exception.BadReservationSaveException;

import java.util.regex.Pattern;

public record ReservationRequest(
        String name,
        String date,
        String time
) {
    private final static String timePattern="\\d{2}:\\d{2}";
    private final static String datePattern="\\d{4}-\\d{2}-\\d{2}";

    public ReservationRequest {
        validate(name,date,time);
    }

    private void validate(String name,String date,String time) {
        validateBlank(name, date, time);
        validateFormat(date,time);
    }

    private void validateBlank(String name,String date,String time) {
        boolean isBlank=false;
        if(name.isBlank()||date.isBlank()||time.isBlank()) isBlank=true;
        if (isBlank) {
            throw new BadReservationSaveException("빈 값이 입력되었습니다.");
        }
    }

    private void validateFormat(String date, String time) {
        if (!date.matches(datePattern)) {
            throw new BadReservationSaveException("날짜 형식이 올바르지 않습니다. (형식: yyyy-MM-dd)");
        }
        if (!time.matches(timePattern)) {
            throw new BadReservationSaveException("시간 형식이 올바르지 않습니다. (형식: HH:mm)");
        }
    }
    public Reservation makeReservation(){
        return new Reservation(name, date, time);
    }

}
