package roomescape.dto.reservation;

import roomescape.domain.ReservationDomain;
import roomescape.domain.TimeDomain;
import roomescape.exception.BadReservationSaveException;

public record ReservationRequest(
        String name,
        String date,
        Long time
) {
    private final static String datePattern="\\d{4}-\\d{2}-\\d{2}";

    public ReservationRequest {
        validate(name,date,time);
    }


    private void validate(String name,String date,Long timeId) {
        validateBlank(name, date, timeId);
        validateFormat(date,timeId);
    }

    private void validateBlank(String name,String date,Long timeId) {
        boolean isBlank=false;
        if(name.isBlank()||date.isBlank()||timeId==null) isBlank=true;
        if (isBlank) {
            throw new BadReservationSaveException("빈 값이 입력되었습니다.");
        }
    }

    private void validateFormat(String date, Long time_id) {
        if (!date.matches(datePattern)) {
            throw new BadReservationSaveException("날짜 형식이 올바르지 않습니다. (형식: yyyy-MM-dd)");
        }
        if (!(time_id instanceof Long)) {
            throw new BadReservationSaveException("시간 아이디 형식이 올바르지 않습니다");
        }
    }
}
