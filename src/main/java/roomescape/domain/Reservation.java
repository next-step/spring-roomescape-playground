package roomescape.domain;

import roomescape.exception.InvalidRequestException;

import java.util.Objects;

public class Reservation {
    private long id;
    private String name;
    private String date;
    private String time;

    private final String INVALID_REQUEST_MESSAGE = "예약 정보에 공백이 입력되었습니다.";

    public Reservation(long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public void validate() {
        if (isBlank(name) || isBlank(date) || isBlank(time)) {
            throw new InvalidRequestException(INVALID_REQUEST_MESSAGE);
        }
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}