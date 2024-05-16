package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.TimeFormatter;

import java.time.LocalDate;
import java.time.LocalTime;

public record SaveReservationRequest(
        String name,
        String date,
        String time
) {
    public SaveReservationRequest {
        validate();
    }
    private void validate() {
        // TODO validation 추가하기
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
