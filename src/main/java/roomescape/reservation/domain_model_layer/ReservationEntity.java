package roomescape.reservation.domain_model_layer;

import lombok.Builder;
import lombok.Getter;
import roomescape.time.domain_model_layer.TimeEntity;

@Getter
public class ReservationEntity {

    // getter
    // 필드
    private Long id;
    private String name;
    private String date;
    private TimeEntity time;

    // 생성자
    @Builder
    public ReservationEntity(Long id, String name, String date, TimeEntity time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

}
