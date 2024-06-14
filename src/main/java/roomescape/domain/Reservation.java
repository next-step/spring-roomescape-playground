package roomescape.domain;


public class Reservation {
    private Long id;
    private String name;
    private String date;
    private Time time; // 객체(object)로 jackson lib가 인식한다.

    public Reservation(Long id, String name, String date, Time time) {
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

    public Time getTime() {
        return time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTime(Time time) {
    }
}
