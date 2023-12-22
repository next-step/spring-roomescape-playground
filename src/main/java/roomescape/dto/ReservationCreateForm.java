package roomescape.dto;

public class ReservationCreateForm {
    private String name;
    private String date;
    private Long time;

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getTime() {
        return time;
    }
}
