package roomescape.data.entity;

import com.fasterxml.jackson.core.ObjectCodec;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ReservationTime {

    private Long id;
    private LocalTime time;

    public ReservationTime(LocalTime time) {
        this.time = time;
    }
}
