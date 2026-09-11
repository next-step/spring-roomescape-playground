package roomescape.domain;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private Time time;

    public Reservation(Long id, String name, String date, Time time) {
        validateName(name);
        validateDate(date);
        validateTime(time);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid name.");
        }
    }

    private void validateDate(String date) {
        if (date == null || date.isBlank()) {
            throw new IllegalArgumentException("Invalid date.");
        }
    }

    private void validateTime(Time time) {
        if (time == null) {
            throw new IllegalArgumentException("Invalid time.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }
}
