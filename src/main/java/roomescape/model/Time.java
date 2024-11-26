package roomescape.model;

public class Time {
    private Long id;
    private String time;

    public Time() {
    }

    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        if (time == null || time.trim().isEmpty()) {
            throw new IllegalArgumentException("시간 값이 비어 있습니다.");
        }
        this.time = time;
    }
}
