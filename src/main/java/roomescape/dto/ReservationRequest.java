package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import roomescape.domain.Reservation;

@Getter
@AllArgsConstructor
public class ReservationRequest {
    private final String name;
    private final LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    public Reservation toDomain(Long id){
        return new Reservation(id, name, date, time);
    }
}
