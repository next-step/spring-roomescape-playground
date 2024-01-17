package roomescape.Entity;

public class Reservation {
        private long id;
        private String name;
        private String date;
        private String time;

    public Reservation(long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static Reservation toEntity(long id, Reservation reservation) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    public void update(Reservation newReservation) {
        this.id = newReservation.id;
        this.name = newReservation.name;
        this.date = newReservation.date;
        this.time = newReservation.time;
    }

    public boolean isEqualId(long id) {
        return this.id == id;
    }
}
