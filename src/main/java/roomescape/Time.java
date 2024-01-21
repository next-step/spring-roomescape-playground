package roomescape;

public class Time {
    private long id;
    private String time;

    public Time(){}
    public Time(String time){
        this.id = 0;
        this.time = time;
    }
    public long getId(){return id;}

    public String getTime(){return time;}

    public void setId(long id){this.id = id;}

    public void setTime(String time){this.time = time;}
}
