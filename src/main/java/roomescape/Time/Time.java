package roomescape.Time;

public class Time {
    private Long id;
    private String time;
    public Time(){}
    public long getId() {return id;}
    public String getTime() {return time;}

    public Time toEntity(Time time) {
        Time newtime = new Time(time.time);
        return newtime;
    }

    public Time(String time) {
        this.time = time;
    }

    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Time{" +
                "id=" + id +
                ", time='" + time + '\'' +
                '}';
    }
}
