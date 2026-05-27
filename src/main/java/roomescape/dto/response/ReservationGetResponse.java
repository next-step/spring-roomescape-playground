package roomescape.dto.response;

import lombok.Getter;
import roomescape.domain.Time;

import java.time.LocalDate;

@Getter
public class ReservationGetResponse {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final Time time;

    public ReservationGetResponse(Long id, String name, LocalDate date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
