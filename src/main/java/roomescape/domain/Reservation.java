package roomescape.domain;

public class Reservation {
    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    public Reservation(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        validateReservation();
    }

    private void validateReservation() {
        if (name == null || name.isBlank()) {
            throw new InvalidReservationException("ERROR: 이름을 작성하여야 합니다.");
        }
        if (date == null || date.isBlank()) {
            throw new InvalidReservationException("ERROR: 날짜를 작성하여야 합니다.");
        }
        if (time == null || time.isBlank()) {
            throw new InvalidReservationException("ERROR: 이름을 작성하여야 합니다.");
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

    public String getTime() {
        return time;
    }
}
