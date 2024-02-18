package roomescape.data.entity;

import java.sql.Time;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReservationTime {

    private Long id;
    private LocalTime time;


    public ReservationTime(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public static ReservationTime from(LocalTime time) {
        return ReservationTime.builder()
                .time(time)
                .build();
    }
}
