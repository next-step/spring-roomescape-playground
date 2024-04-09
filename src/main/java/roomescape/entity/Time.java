package roomescape.entity;

public class Time {

    private Long id;
    private String time;

    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Time{" +
                "id=" + id +
                ", time='" + time + '\'' +
                '}';
    }

}
