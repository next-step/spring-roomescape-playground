package roomescape.domain;

import lombok.Builder;

public class Reservation {

    // 필드
    private Long id;
    private String name;
    private String date;
    private Time time;

    // 생성자
    @Builder
    public Reservation(Long id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    // getter
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

    public void setId(Long generatedId) {
    }
}
