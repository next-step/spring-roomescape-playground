package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.TimeFormatter;
import roomescape.domain.Times;
import roomescape.exception.IllegalSaveReservaionException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public record SaveReservationRequest(
        String date,
        String name,
        Long timeId
) {

    public SaveReservationRequest {
        validate(name, date, timeId);
    }

    private void validate(String name, String date, Long timeId) {
        validateBlank(name, date);
        validateTimeIdRange(timeId);
    }

    private void validateBlank(String ... fields) {
        boolean isBlankExist = Arrays.stream(fields)
                   .anyMatch(String::isBlank);
        if(isBlankExist){
            throw new IllegalSaveReservaionException("빈 값을 포함하면 안됩니다.");
        }
    }

    private void validateTimeIdRange(Long timeId){
        if(timeId < 1){
            throw new IllegalArgumentException("존재하지 않는 시간입니다.");
        }
    }

    public Reservation toReservation(Times time){
        LocalDate localDate = LocalDate.parse(this.date, TimeFormatter.dateFormatter);
        return new Reservation(
                this.name,
                localDate,
                time
        );
    }
}
