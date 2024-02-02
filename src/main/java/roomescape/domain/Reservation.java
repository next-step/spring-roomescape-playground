package roomescape.domain;

public class Reservation {
        private Long time_id;
        private String name;
        private String date;
        private Time time;

    public Reservation(Long id, String name, String date, Time time) {
        this.time_id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return time_id;
    }

    public void setId(Long id) {
        this.time_id = id;
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

    public boolean isEqualId(Long id) {
        return this.time_id == id;
    }
}
