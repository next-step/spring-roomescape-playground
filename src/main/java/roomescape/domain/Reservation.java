package roomescape.domain;

import roomescape.exception.InvalidRequestException;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private Time time;

    private final String INVALID_NAME_REQUEST_MESSAGE = "이름 정보가 공백입니다.";
    private final String INVALID_DATE_REQUEST_MESSAGE = "날짜 정보가 공백입니다.";

    public Reservation(long id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void Validate(){
        nameValidate();
        dateValidate();
    }

    public void nameValidate() {
        if (isBlank(name)) {
            throw new InvalidRequestException(INVALID_NAME_REQUEST_MESSAGE);
        }
    }

    public void dateValidate() {
        if (isBlank(date)) {
            throw new InvalidRequestException(INVALID_DATE_REQUEST_MESSAGE);
        }
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}