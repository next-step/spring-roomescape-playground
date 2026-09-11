package roomescape.domain;

public class Time {

    private Long id;
    private String time;

    public Time(Long id, String time) {
        validateTime(time);
        this.id = id;
        this.time = time;
    }

    private void validateTime(String time) {
        if (time == null || time.isBlank()) {
            throw new IllegalArgumentException("Invalid time.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
