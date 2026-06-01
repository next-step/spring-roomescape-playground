package roomescape.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ReservationGetResponse {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final Long timeId;
    private final LocalTime timeStartAt;


    public ReservationGetResponse(Long id, String name, LocalDate date, Long timeId, LocalTime timeStartAt) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
        this.timeStartAt = timeStartAt;
    }
}
