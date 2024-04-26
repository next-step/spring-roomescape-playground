package roomescape.domain;


import lombok.Builder;

public class Time {
    private Long id;
    private String time;

    @Builder
    public Time(Long id, String time) {
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
