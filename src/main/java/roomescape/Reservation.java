package roomescape;

public class Reservation {
    private int id;
    private String name;
    private String date;
    private String time;

    public Reservation() {
    }

    public Reservation(int id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime(){
        return time;
    }
    public static Reservation toEntity(Reservation reservation, int id) {
        return new Reservation(id, reservation.name, reservation.date,reservation.time);
    }
    public void update(Reservation newReservation) {
        this.name = newReservation.name;
        this.date = newReservation.date;
        this.time = newReservation.time;
    }
}
