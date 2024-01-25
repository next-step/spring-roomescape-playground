package roomescape.domain;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private Time time;

    public Reservation() { }

    public Reservation(Long id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, String date, Time time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public Time getTime() { return time; }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    public static boolean checkValidity(Reservation reservation) {
        return (reservation.getName().isEmpty() ||
                reservation.getDate().isEmpty() ||
                reservation.getTime().getId() == null);
    }

    public void update(Reservation newReservation) {
        this.name = newReservation.name;
        this.date = newReservation.date;
        this.time = newReservation.time;
    }
}
