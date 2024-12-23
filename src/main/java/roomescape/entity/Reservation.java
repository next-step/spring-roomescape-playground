package roomescape.entity;

import java.time.LocalTime;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private roomescape.entity.Time time; // 명시적으로 roomescape.entity.Time을 사용

    public Reservation(Long id, String name, String date, roomescape.entity.Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = new roomescape.entity.Time(time); // 명시적으로 roomescape.entity.Time 생성자 호출
    }

    public Reservation(String name, String date, roomescape.entity.Time time) {
        this(null, name, date, time);
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

    public roomescape.entity.Time getTime() { // 반환 타입에도 패키지 명시
        return time;
    }

    public LocalTime getTimeAsLocalTime() {
        return time.getTimeAsLocalTime();
    }
}
