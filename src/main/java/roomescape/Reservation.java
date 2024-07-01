package roomescape;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation(){}

    public Reservation(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, String data, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() { return id; }

    public String getDate() { return date; }

    public String getName() { return name; }

    public String getTime() { return time; }

//    public void update(Reservation newReservation){
//        this.name = newReservation.name;
//        this.date = newReservation.date;
//        this.time = newReservation.time;
//    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }
}