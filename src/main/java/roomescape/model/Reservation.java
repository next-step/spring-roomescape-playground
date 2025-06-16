package roomescape.model;

import roomescape.exception.BadRequestException;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private Time time;

    public Reservation() {
    }

    public Reservation(String name, String date, Time time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(Long id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        validate();
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

    public Time getTime() {
        return time;
    }

    private void validate() {
        if (this.name == null || this.name.isEmpty()) {
            throw new BadRequestException("이름을 작성해주세요");
        }
        if (this.date == null || this.date.isEmpty()) {
            throw new BadRequestException("날짜를 선택해주세요");
        }
        if (this.time == null) {
            throw new BadRequestException("시간을 선택해주세요");
        }
    }

}
