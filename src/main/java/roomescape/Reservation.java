package roomescape;

import static org.springframework.util.ObjectUtils.isEmpty;

public class Reservation {

    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    public Reservation(final Long id, final String name, final String date, final String time) {
        if (isEmpty(name) || isEmpty(date) || isEmpty(time)) {
            throw new IllegalArgumentException("예약 정보를 모두 입력해주세요.");
        }
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
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
