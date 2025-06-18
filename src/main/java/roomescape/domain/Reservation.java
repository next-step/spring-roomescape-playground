package roomescape.domain;

public class Reservation {
    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    private Reservation(Long id, String name, String date, String time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation of(Long id, String name, String date, String time) {
        return new Reservation(id, name, date, time);
    }

    private void validate(String name, String date, String time) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("날짜 형식은 yyyy-MM-dd 이어야 합니다.");
        }
        if (!time.matches("\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("시간 형식은 HH:mm 이어야 합니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

}

