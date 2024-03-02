package roomescape.DTO;

import roomescape.Domain.Time;

public class TimeResponseDTO{

    private Long id;
    private String time;

    public TimeResponseDTO(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public static TimeResponseDTO from(Time time) {
        return new TimeResponseDTO(time.getId(), time.getTime());
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
