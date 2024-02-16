package roomescape.domain;

public class Time {
    private long id;
    private String time;

    public Time(){}

    public Time(String time){
        this.id = 0;
        this.time = time;
    }

    public Time(long id, String time){
        this.id = id;
        this.time = time;
    }

    public Time toEntity(long id, String time){
        return new Time(id, time);
    }

    public long getId(){
        return id;
    }

    public String getTime(){
        return time;
    }

    public boolean isInvalid() {
        return time.matches("^([01]\\d|2[0-3]):([0-5]\\d)$");
    }
}
