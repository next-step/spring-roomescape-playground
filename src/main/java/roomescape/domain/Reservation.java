package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import static io.micrometer.common.util.StringUtils.isBlank;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(Long id, String name, String date, String time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = LocalDate.parse(date);
        this.time = LocalTime.parse(time);
    }

    private void validate(String name, String date, String time) {
        if (isBlank(name) || isBlank(date) || isBlank(time)) {
            throw new IllegalArgumentException("예약을 생성할 수 없습니다. 에약자, 날짜, 시간이 모두 필요합니다.");
        }
    }

    public boolean isSameId(long id) {
        return this.id == id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
