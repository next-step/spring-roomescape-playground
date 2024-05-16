package roomescape.domain;

public class Reservation {
    private long id;
    private String name;
    private String date;
    private String time;

    public Reservation(){}

    public long getId() {return this.id;}
    public String getName() {return this.name;}
    public String getDate() {return this.date;}
    public String getTime() {return this.time;}

    public long setId(long id) {return this.id = id;}
    public String setName(String name) {return this.name = name;}
    public String setDate(String date) {return this.date = date;}
    public String setTime(String time) {return this.time = time;}
}
