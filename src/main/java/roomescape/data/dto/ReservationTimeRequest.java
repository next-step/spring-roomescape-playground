package roomescape.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import roomescape.data.entity.ReservationTime;

@Getter
@AllArgsConstructor
@Builder
public class ReservationTimeRequest {

    private Long id;
    private String time;
}
