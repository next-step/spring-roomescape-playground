package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

public class ReservationRequest {
    private String name;
    private String date;
    private Long timeId;

    public ReservationRequest() {
    }

    public ReservationRequest(String name, String date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public Reservation toEntity(Time time) {
        return new Reservation(null, name, LocalDate.parse(date), time);
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }
}
