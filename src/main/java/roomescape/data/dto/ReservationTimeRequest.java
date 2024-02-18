package roomescape.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Time;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import roomescape.data.entity.ReservationTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationTimeRequest {

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
}
