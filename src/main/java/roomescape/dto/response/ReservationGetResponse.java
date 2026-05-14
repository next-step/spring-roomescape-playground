package roomescape.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ReservationGetResponse {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public ReservationGetResponse(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
