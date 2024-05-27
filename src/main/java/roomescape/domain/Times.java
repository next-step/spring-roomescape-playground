package roomescape.domain;

import java.time.LocalTime;

public class Times {
    private Long id;
    private LocalTime time;

    public Times() {
    }

    public Times(LocalTime time) {
        this.time = time;
    }

    public Times(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setId(long id) {
    }

    public Long getId() {
        return id;
    }
}
