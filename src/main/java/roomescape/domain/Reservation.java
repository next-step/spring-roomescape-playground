package roomescape.domain;

public class Reservation {
    private int id;
    private String name;
    private String date;
    private String time;

    public Reservation(int id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public int getId() {return this.id;}
    public String getName() {return this.name;}
    public String getDate() {return this.date;}
    public String getTime() {return this.time;}

    public int setId(int id) {return this.id = id;}
    public String setName(String name) {return this.name = name;}
    public String setDate(String date) {return this.date = date;}
    public String setTime(String time) {return this.time = time;}
}
