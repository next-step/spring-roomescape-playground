package roomescape.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReservationTime {

    private Long id;
    private String time;

    public ReservationTime(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public static ReservationTime from(String time) {
        return ReservationTime.builder()
                .time(time)
                .build();
    }
}
