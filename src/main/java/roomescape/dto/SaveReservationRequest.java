package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.TimeFormatter;
import roomescape.exception.IllegalSaveReservaionException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public record SaveReservationRequest(
        String date,
        String name,
        String time
) {
    public SaveReservationRequest {
        validate(name, date, time);
    }
    private void validate(String name, String date, String time) {
        validateBlank(name, date, time);
    }
    private void validateBlank(String... fields) {
        boolean isBlankExist = Arrays.stream(fields)
                   .anyMatch(String::isBlank);
        if(isBlankExist){
            throw new IllegalSaveReservaionException("빈 값을 포함하면 안됩니다.");
        }
    }

    public Reservation toReservation(){
        LocalDate date = LocalDate.parse(this.date, TimeFormatter.dateFormatter);
        LocalTime time = LocalTime.parse(this.time, TimeFormatter.timeFormatter);

        return new Reservation(
                this.name,
                date,
                time
        );
    }
}
