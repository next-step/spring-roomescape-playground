package roomescape.DTO;

public class ReservationRequestDTO {
    private String name;
    private String date;
    private Long time;

    ReservationRequestDTO() {
    }

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
