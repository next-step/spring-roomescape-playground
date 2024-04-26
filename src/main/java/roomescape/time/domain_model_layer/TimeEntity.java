package roomescape.time.domain_model_layer;


import lombok.Builder;

public class TimeEntity {
    private Long id;
    private String time;

    @Builder
    public TimeEntity(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
