package roomescape.dto;

public class TimeAddRequest {
    private final String time;

    private TimeAddRequest(){
        this(null);
    }

    public TimeAddRequest(String time){
        this.time = time;
    }

    public String getTime(){
        return time;
    }
}
