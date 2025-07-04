package roomescape.domain;

public class Time {
    private final Long id;
    private final String time;

    private Time(Long id, String time) {
        validate(time);
        this.id = id;
        this.time = time;
    }

    private void validate(String time) {
        if (time == null) {
            throw new IllegalArgumentException("시간 값은 null일 수 없습니다.");
        }
        if (time.isBlank()) {
            throw new IllegalArgumentException("시간 값은 빈 문자열일 수 없습니다.");
        }
    }

    public static Time of(Long id, String time) {
        return new Time(id, time);
    }

    public static Time of(String time) {
        return of(null, time);
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}

