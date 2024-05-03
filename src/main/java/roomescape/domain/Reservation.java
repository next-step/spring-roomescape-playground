package roomescape.domain;

import java.util.concurrent.atomic.AtomicLong;

public class Reservation {
    private static AtomicLong index = new AtomicLong(0);

    private long id;
    private String name;
    private String date;
    private String time;

    public Reservation(){
        this.id = index.incrementAndGet();
    }

    public Reservation(String name, String date, String time) {
        this.id = index.incrementAndGet();
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId() {return this.id;}
    public String getName() {return this.name;}
    public String getDate() {return this.date;}
    public String getTime() {return this.time;}

    public long setId() {return this.id = index.incrementAndGet();}
    public String setName(String name) {return this.name = name;}
    public String setDate(String date) {return this.date = date;}
    public String setTime(String time) {return this.time = time;}
}
