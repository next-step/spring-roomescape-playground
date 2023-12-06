package roomescape.Time;

public class Time {
    private Long id;
    private String time;

    public Time(){}
    public Time(String time) {
        this.time = time;
    }
    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Time toEntity(Time time) {
        Time newtime = new Time(time.time);
        return newtime;
    }

    public long getId() {return id;}
    public String getTime() {return time;}





}
