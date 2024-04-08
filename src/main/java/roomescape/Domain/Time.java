package roomescape.Domain;

public class Time {
    private Long id;
    private String time;

    public Time(Long id, String time){
        this.id=id;
        this.time=time;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public Long getId(){ return id; }
    public String getTime() {
        return time;
    }

    @Override
    public String toString(){
        return time;
    }

}
