package roomescape.reservation.domain_model_layer;

import lombok.Builder;
import roomescape.time.domain_model_layer.TimeEntity;

public class ReservationEntity {

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

    // getter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }


    public TimeEntity getTime() {
        return time;
    }

    public void setId(Long generatedId) {
    }
}
