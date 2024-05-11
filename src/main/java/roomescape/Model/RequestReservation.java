package roomescape.Model;

public class RequestReservation {

    private String name;
    private String time;
    private String date;

    public RequestReservation(String name,String time,String date){
        this.date=date;
        this.time=time;
        this.name=name;
    }

    public String getName(){
        return  name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
