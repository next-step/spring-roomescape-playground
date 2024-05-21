package roomescape.Model;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation(){

    }
    public Reservation(Long id,String name,String date,String time){
        this.id=id;
        this.name = name;
        this.time=time;
        this.date=date;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

   public String getTime(){
        return time;
   }

   public String getDate(){
        return date;
   }

}