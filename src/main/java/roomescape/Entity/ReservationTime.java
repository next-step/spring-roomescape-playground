package roomescape.Entity;

public class ReservationTime {

    private Long id;
    private String time;

    public ReservationTime(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public Long getId() {
        return id;
    }
}
