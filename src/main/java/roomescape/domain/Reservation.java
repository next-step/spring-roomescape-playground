package roomescape.domain;

import static io.micrometer.common.util.StringUtils.isBlank;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        validate(name, date, time);
    }

    private void validate(String name, String date, String time) {
        if (isBlank(name) || isBlank(date) || isBlank(time)) {
            throw new IllegalArgumentException("예약을 생성할 수 없습니다. 에약자, 날짜, 시간이 모두 필요합니다.");
        }
    }

    public long getId() {
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
