package roomescape.domain;

import roomescape.exception.NoParameterException;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private Time time;

    private Reservation() { }

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
        checkValidity();
    }

    private void checkValidity() {
        if (this.getName().isEmpty() ||
                this.getDate().isEmpty() ||
                this.getTime().getId() == null)
            throw new NoParameterException();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public Time getTime() { return time; }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    public void update(Reservation newReservation) {
        this.name = newReservation.name;
        this.date = newReservation.date;
        this.time = newReservation.time;
    }
}
