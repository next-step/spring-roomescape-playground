package roomescape.dto;

public class ReservationSaveRequestDto {

    private String name;
    private String date;
    private Long time;

    public ReservationSaveRequestDto() {
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
